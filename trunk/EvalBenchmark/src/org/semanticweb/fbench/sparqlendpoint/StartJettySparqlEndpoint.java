package org.semanticweb.fbench.sparqlendpoint;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.semanticweb.fbench.misc.TimedInterrupt;
import org.semanticweb.fbench.misc.Utils;


public class StartJettySparqlEndpoint {
	
	private static Logger log;
	
	public static String repositoryType = null;
	public static String repositoryLocation = null;
	public static int port = 0;
	public static int nWorkerThreads = 8;
	protected static Server server = null;
	protected static File pidFile = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		if (args.length!=2 && args.length!=3)
			printHelpAndExit();
		
		repositoryType = "native";
		repositoryLocation = args[0];
			
		port = Integer.parseInt(args[1]);
		if (args.length==3)
			nWorkerThreads = Integer.parseInt(args[2]);
		String host = "localhost";
		
		if (System.getProperty("log4j.configuration")==null)
			System.setProperty("log4j.configuration", "file:config/log4j-sparql.properties");
		log = Logger.getLogger(StartJettySparqlEndpoint.class);
		
		System.setProperty("org.mortbay.io.nio.MAX_SELECTS", "50000");	// XXX check if this fixes the busy bugs
		
		writePIDFile();	// write a file of %PID%.pid such that process can be killed if it does not terminate
		
        new GracefullShutdownThread().start();
        
		server = new Server();
        Connector connector = new SelectChannelConnector();
        connector.setPort(port);
        connector.setHost(host);
        connector.setMaxIdleTime(30*60*1000);	// 30 min
        server.addConnector(connector);

        WebAppContext wac = new WebAppContext();
        wac.setContextPath("/");
        wac.setWar("config/jetty/sparql/");
        wac.getSessionHandler().getSessionManager().setMaxInactiveInterval(30*60); // sets to 30 min 
        server.setHandler(wac);
        server.setStopAtShutdown(true);

        server.start();		
        
	}

	
	protected static void printHelpAndExit() {
		System.out.println("Usage: \n" +
				"\tstartSparqlEndpoint <RepositoryLocation> <Port>\n" +
				"\tstartSparqlEndpoint <RepositoryLocation> <Port> <WorkerThreads>\n");
		System.exit(1);
	}
	
	
	protected static void writePIDFile() throws IOException {
		
		long pid = Utils.getPID();
		pidFile = new File(pid + ".pid");
		pidFile.createNewFile();
	}
	
	protected static class GracefullShutdownThread extends Thread {
		
		private File cFile = new File("_shutdown");
		
		@Override
		public void run() {
			
			log.info("Started GracefullShutdownThread...");
			while (true) {
				
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					
				}
				if (cFile.exists()) {
					log.info("Gracefull shutdown request ... ");
					try {
						boolean success = new TimedInterrupt().run( new Runnable() {
							@Override
							public void run() {
								try {
									server.stop();
								} catch (Exception e) {
									log.error("Error closing conenction.", e);
									exit(1);
								}						
							}
						}, 30000);
					
						if (!success)
							exit(1);
										
					} catch (Exception e) {
						log.error("Stopping the server failed.", e);
						System.exit(1);
					}
					exit(0);
				}
			}
		}
		
		
		protected void exit(int code) {
			if (pidFile!=null)
				pidFile.delete();
			System.exit(code);
		}
	}
}
