package org.semanticweb.fbench.evaluation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openrdf.model.Graph;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.model.impl.GraphImpl;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.Repository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.semanticweb.fbench.provider.RepositoryProvider;
import org.semanticweb.fbench.provider.SPARQLProvider;
import org.semanticweb.fbench.report.ReportStream;

import com.fluidops.fedx.evaluation.RepositoryTripleSource;
import com.fluidops.fedx.evaluation.SparqlTripleSource;
import com.fluidops.fedx.evaluation.TripleSource;
import com.fluidops.fedx.structures.Endpoint;
import com.fluidops.fedx.structures.Endpoint.EndpointClassification;





public class DataConfigReader {

	
	
	public static List<Endpoint> loadFederationMembers(File dataConfig, ReportStream ...report) throws IOException, Exception {
			
		Graph graph = new GraphImpl();
		RDFParser parser = Rio.createParser(RDFFormat.N3);
		RDFHandler handler = new DefaultRDFHandler(graph);
		parser.setRDFHandler(handler);
		parser.parse(new FileReader(dataConfig), "http://fluidops.org/config#");
		
		List<Endpoint> res = new ArrayList<Endpoint>();
		Iterator<Statement> iter = graph.match(null, new URIImpl("http://fluidops.org/config#store"), null);
				
		while (iter.hasNext()){
			Statement s = iter.next();
			Endpoint e = loadEndpoint(graph, s.getSubject(), s.getObject(), report);
			res.add(e);
		}
		
		return res;
	}
	
	
	
	protected static Endpoint loadEndpoint(Graph graph, Resource repNode, Value repType, ReportStream ...report) throws Exception {
		RepositoryProvider repProvider;
		long datasetLoadStart = System.currentTimeMillis();
		
		TripleSource tripleSource;
		EndpointClassification epClass;
		if (repType.equals(new LiteralImpl("NativeStore"))){
			repProvider = new NativeStoreProvider();
			tripleSource = RepositoryTripleSource.getInstance();
			epClass = EndpointClassification.Local;
		} else if (repType.equals(new LiteralImpl("SPARQLEndpoint"))){
			repProvider = new SPARQLProvider();
			tripleSource = SparqlTripleSource.getInstance();
			epClass = EndpointClassification.Remote;
		} else {
			throw new RuntimeException("Repository type not supported: " + repType.stringValue());
		}
				
		Repository rep = repProvider.load(graph, repNode);
		long datasetLoadDuration = System.currentTimeMillis()-datasetLoadStart;
		
		String endpoint = repProvider.getLocation(graph, repNode);
		String name = repNode.stringValue();
		String id = repProvider.getId(graph, repNode);
		String type = repType.stringValue();
		
		Endpoint res = new Endpoint(id,name,endpoint,type,epClass);
		res.setRepo(rep, tripleSource, true);
		
		if (report.length!=0)
			report[0].reportDatasetLoadTime(id, name, endpoint, type, datasetLoadDuration);
		
		return res;
	}
	
	
	
	
	
	protected static class DefaultRDFHandler implements RDFHandler {

		protected final Graph graph;
				
		public DefaultRDFHandler(Graph graph) {
			super();
			this.graph = graph;
		}

		@Override
		public void endRDF() throws RDFHandlerException {
			; // no-op
		}

		@Override
		public void handleComment(String comment) throws RDFHandlerException {
			; // no-op			
		}

		@Override
		public void handleNamespace(String prefix, String uri)
				throws RDFHandlerException {
			; // no-op			
		}

		@Override
		public void handleStatement(Statement st) throws RDFHandlerException {
			graph.add(st);			
		}

		@Override
		public void startRDF() throws RDFHandlerException {
			; // no-op			
		}
		
		
	}
}
