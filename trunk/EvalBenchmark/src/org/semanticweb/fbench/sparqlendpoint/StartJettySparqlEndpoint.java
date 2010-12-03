package org.semanticweb.fbench.sparqlendpoint;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;


public class StartJettySparqlEndpoint {
	
	public static String repositoryType = null;
	public static String repositoryLocation = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		if (args.length!=2)
			printHelpAndExit();
		
		repositoryType = "native";
		repositoryLocation = args[0];
			
		int port = Integer.parseInt(args[1]);
		String host = "localhost";
		
		if (System.getProperty("log4j.configuration")==null)
			System.setProperty("log4j.configuration", "file:config/log4j-sparql.properties");
		
		Server server = new Server();
        Connector connector = new SelectChannelConnector();
        connector.setPort(port);
        connector.setHost(host);
        server.addConnector(connector);

        WebAppContext wac = new WebAppContext();
        wac.setContextPath("/");
        wac.setWar("config/jetty/sparql/");
        server.setHandler(wac);
        server.setStopAtShutdown(true);

        server.start();		
	}

	
	protected static void printHelpAndExit() {
		System.out.println("Usage: \n" +
				"\tstartSparqlEndpoint <RepositoryLocation> <Port>\n");
		System.exit(1);
	}
}
