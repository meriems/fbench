package org.semanticweb.fbench.proxy;

import java.io.File;

import org.mortbay.jetty.Server;
import org.mortbay.xml.XmlConfiguration;

/**
 * This executable starts a Jetty server that acts as proxy for our
 * use case scenario. 
 * 
 * In particular the configuration registers the MyAsyncProxyServlet
 * which forwards HTTP requests to the URL specified within the 
 * parameter.
 * 
 * Example:
 * 
 * http://localhost:2000/http://myEndpoint.com:80/sparql
 * 
 * In the above example the request is forwarded to 
 * http://myEndpoint.com:80/sparql. Note that in the general case
 * this URL is urlEncoded.
 * 
 * Usage:
 * 
 * startProxy
 * startProxy <RequestHandler>
 * startProxy <RequestHandler> <RequestDelay>
 * startProxy <RequestHandler> <RequestDelay> <JettyConfig>
 * 
 * Params:
 * <RequestHandler> - the fully qualified class implementing a RequestHanlder
 * 						default: {@link DelayRequestHandler}
 * <RequestDelay> - the delay in ms (e.g. used in {@link DelayRequestHandler}
 * 						default: 100
 * <JettyConfig> - the jetty config file to be used
 * 						default: config/jetty/jetty.xml
 * 
 * @author as
 *
 */
public class StartJettyProxy {
	
	/**
	 * Default request delay in ms
	 */
	public static final long DEFAULT_REQUEST_DELAY = 100;	
	
	public static String requestHandler = null;
	public static long requestDelay = DEFAULT_REQUEST_DELAY;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		String jettyCfg = "config/jetty/jetty.xml";
		
		if (args.length==0) {
			requestHandler = DelayRequestHandler.class.getCanonicalName();
		}
		
		// request handler is specified
		else if (args.length==1) {
			requestHandler = args[0];
		} 
		
		else if (args.length==2) {
			requestHandler = args[0];
			requestDelay = Long.parseLong(args[1]);
		}
		
		else if (args.length==3) {
			requestHandler = args[0];
			requestDelay = Long.parseLong(args[1]);
			jettyCfg = args[2];
		}
		
		else {
			printHelpAndExit();
		}
		
		if (System.getProperty("log4j.configuration")==null)
			System.setProperty("log4j.configuration", "file:config/log4j.properties");
		Server server = new Server();
		XmlConfiguration configuration = new XmlConfiguration(new File(jettyCfg).toURI().toURL());
	    configuration.configure(server);
	    server.start();
	}

	
	protected static void printHelpAndExit() {
		System.out.println("Usage: \n" +
				"\tstartProxy\n" +
				"\tstartProxy <RequestHandler>\n" +
				"\tstartProxy <RequestHandler> <RequestDelay>\n" +
				"\tstartProxy <RequestHandler> <RequestDelay> <JettyConfig>");
		System.exit(1);
	}
}
