package org.semanticweb.fbench.provider;

import java.io.File;
import java.util.Iterator;

import org.openrdf.model.Graph;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.sail.nativerdf.NativeStore;
import org.semanticweb.fbench.misc.FileUtil;



/**
 * Provider to fill a native Sesame store.<p>
 * 
 * Sample dataConfig:<p>
 * 
 * <code>
 * 
 * relative Path for storeFile
 * 
 * <http://NYTimes.Locations> fluid:store "Native";
 * fluid:storeFile "data\\native-storage.SingleStore.Cross";
 * fluid:rdfFile "D:\\datasets\\nytimes\\locations.rdf";
 * fluid:context <http://nytimes.org>.
 * 
 * 
 * absolute Path for storeFile:
 * 
 * <http://NYTimes.Organizations> fluid:store "Native";
 * fluid:storeFile "D:\\data\\native-storage.SingleStore.Cross";
 * fluid:rdfFile "D:\\datasets\\nytimes\\organizations.rdf";
 * fluid:context <http://nytimes.org>.
 * </code>
 * 
 * @author (mz), as
 *
 */
public class NativeStoreFiller implements RepositoryProvider {

	private Repository rep;
	
	@Override
	public Repository load(Graph graph, Resource repNode) throws Exception {
		Iterator<Statement> iter = graph.match(repNode, new URIImpl("http://fluidops.org/config#rdfFile"), null);
		Statement s = iter.next();
		String fileName = s.getObject().stringValue();
		iter = graph.match(repNode, new URIImpl("http://fluidops.org/config#storeFile"), null);
		s = iter.next();
		String storeFile = s.getObject().stringValue();
		iter = graph.match(repNode, new URIImpl("http://fluidops.org/config#context"), null);
		s = iter.next();
		String context = s.getObject().toString();
		
		File rdfFile = FileUtil.getFileLocation(fileName);
		if (!rdfFile.exists())
			throw new RuntimeException("RDF file does not exist at '" + fileName + "'.");
				
		File store = FileUtil.getFileLocation(storeFile);
		NativeStore ns = new NativeStore(store);		
		rep = new SailRepository(ns);
		rep.initialize();
    	RDFFormat rdfFormat = RDFFormat.forFileName(rdfFile.getName());
    	URI u = ValueFactoryImpl.getInstance().createURI(context);
    	System.out.println("Adding dataset under context " + u.toString());
    	if (rdfFormat != null){
    		RepositoryConnection conn = rep.getConnection();
    		try {
    			conn.add(rdfFile, null, rdfFormat, u);
    		}
    		finally {
    			conn.close();
    			rep.shutDown();
    		}
    	}

		return null;
	}

	@Override
	public String getLocation(Graph graph, Resource repNode) {
		Iterator<Statement> iter = graph.match(repNode, new URIImpl("http://fluidops.org/config#rdfFile"), null);
		Statement s = iter.next();
		String fileName = s.getObject().stringValue();
		iter = graph.match(repNode, new URIImpl("http://fluidops.org/config#storeFile"), null);
		s = iter.next();
		String storeFile = s.getObject().stringValue();
		return fileName + " [storeFile: " + storeFile + "]";
	}

}
