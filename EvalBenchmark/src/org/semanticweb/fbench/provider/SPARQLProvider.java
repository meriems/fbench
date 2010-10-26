package org.semanticweb.fbench.provider;

import java.util.Iterator;

import org.openrdf.model.Graph;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.sparql.SPARQLRepository;


/**
 * Provider to integrate a SPARQL endpoint into a Sail.<p>
 * 
 * Sample dataConfig:<p>
 * 
 * <code>
 * <http://DBpedia> fluid:store "SPARQLEndpoint";
 * fluid:SPARQLEndpoint "http://dbpedia.org/sparql".
 * 
 * <http://NYtimes> fluid:store "SPARQLEndpoint";
 * fluid:SPARQLEndpoint "http://api.talis.com/stores/nytimes/services/sparql".
 * </code>
 * 
 * @author (mz), as
 *
 */
public class SPARQLProvider implements RepositoryProvider {

	@Override
	public Repository load(Graph graph, Resource repNode) throws Exception {
		Iterator<Statement> iter = graph.match(repNode, new URIImpl("http://fluidops.org/config#SPARQLEndpoint"), null);
		Statement s = iter.next();
		String sparqlEndpoint = s.getObject().stringValue();
		
		SPARQLRepository rep = new SPARQLRepository(sparqlEndpoint);
		rep.initialize();
	
		return rep;
	}

	@Override
	public String getLocation(Graph graph, Resource repNode) {
		Iterator<Statement> iter = graph.match(repNode, new URIImpl("http://fluidops.org/config#SPARQLEndpoint"), null);
		Statement s = iter.next();
		String sparqlEndpoint = s.getObject().stringValue();
		return sparqlEndpoint;
	}

	@Override
	public String getId(Graph graph, Resource repNode) {
		String id = repNode.stringValue().replace("http://", "");
		id = id.replace("/", "_");
		return "sparql_" + id;
	}

}
