package org.semanticweb.fbench.provider;

import java.io.File;
import java.util.Iterator;

import org.openrdf.model.Graph;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.Repository;

import de.uni_koblenz.west.federation.helpers.RepositoryCreator;

public class RDFFederatorProvider implements RepositoryProvider {
	
	@Override
	public Repository load(Graph graph, Resource repNode) throws Exception {
		Iterator<Statement> iter = graph.match(repNode, new URIImpl("http://fluidops.org/config#rdfFile"), null);
		String fileName = iter.next().getObject().stringValue();
		return new RepositoryCreator().load(new File(fileName).toURI().toURL());
	}
	
	@Override
	public String getId(Graph graph, Resource repNode) {
		String id = repNode.stringValue().replace("http://", "");
		return id.replace("/", "_");
	}

	@Override
	public String getLocation(Graph graph, Resource repNode) {
		return "RDFFederator: unknown location";
	}

}
