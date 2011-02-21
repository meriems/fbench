package org.semanticweb.fbench.evaluation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openrdf.model.Graph;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.GraphImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.semanticweb.fbench.Config;
import org.semanticweb.fbench.misc.Utils;
import org.semanticweb.fbench.query.Query;
import org.semanticweb.fbench.report.SparqlQueryRequestReport;
import org.semanticweb.fbench.report.VoidReportStream;



/**
 * Implementation for query evaluation based on original Sesame Build
 *
 * This implementation automatically starts SPARQL endpoints! See suites
 * for configuration example
 * 
 * @author as
 */
public class SesameSparqlEvaluation extends SesameEvaluation {

	public static Logger log = Logger.getLogger(SesameSparqlEvaluation.class);

	protected int runningServers = 0;
	protected long extraWait = 20000;					// wait time for servers
	protected List<RepoInformation> repoInformation;	// repository information (id, location for local servers, url)
	
	protected SparqlQueryRequestReport sparqlReport = null;		// if not null, this report is used to collect request count stats
	
	public SesameSparqlEvaluation() {
		super();
	}
	
	@Override
	public void finish() throws Exception {
		super.finish();
		if (sparqlReport!=null)
			sparqlReport.finish();
		File cFile = new File("_shutdown");
		cFile.createNewFile();
		Thread.sleep(3000);	// give servers the chance to see the file
		cFile.delete();
	}

	@Override
	public void initialize() throws Exception {
		super.initialize();
		new File("_shutdown").delete();
		for (File f : new File(".").listFiles())
			if (f.getName().endsWith(".pid"))
				f.delete();
		repoInformation = getRepoInformation();
		
		if (Config.getConfig().isSparqlRequestReport()) {
			sparqlReport = new SparqlQueryRequestReport();
			sparqlReport.init(repoInformation);
		}
		reinitializeSystem();
	}

	@Override
	public int runQuery(Query q) throws Exception {
		return super.runQuery(q);
	}
	
	@Override
	public void reInitialize() throws Exception {
		reinitializeSystem();		
	}
	
	
	@Override
	protected void queryRunEnd(Query query, boolean error) {
		
		if (sparqlReport!=null) {
			try {
				sparqlReport.handleQuery(query);
			} catch (IOException e) {
				// ignore
			}
		}
		
		if (error)
			return;
		
		try {
			log.debug("Query " + query.getIdentifier() + " done. Giving SPARQL endpoint a break of 5000ms.");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// ignore
		}
		
		
	}
	
	
	protected void reinitializeSystem() throws Exception {
		log.info("Reinitializing system...");
		
		if (runningServers!=0) {
			log.info("Wating for graceful shutdown of servers, give them " + extraWait + "ms time.");
			File cFile = new File("_shutdown");
			cFile.createNewFile();
			System.gc();
			Thread.sleep(extraWait);
			cFile.delete();
			log.info("Checking for processes that did not shutdown gracefully and killing them by force..");
			checkAndKillServers();			
			runningServers=0;
		}
		
		
		log.info("Trying to close connection: " + conn.getClass().getCanonicalName() + " (" + conn.getSailConnection().getClass() + ")");
		boolean _closed = Utils.closeConnectionTimeout(conn, 10000);
		log.info( _closed ? "Connection closed successfully." : "Error closing connection, timeout occured.");
				
		log.info("Trying to shutdown repository.");
		sailRepo.shutDown();
		
		log.info("Deleting possible locks in the file system.");
		for (RepoInformation r : repoInformation) {
			if (r.repoLoc == null)
				continue;
			for (File f : r.repoLoc.listFiles())
				if (f.isDirectory() && f.getName().equals("lock")) {
					if (!deleteFolder(f))
						log.fatal("Lock folder " + f.getAbsolutePath() + " could not be deleted!");
				}
		}
		
		log.info("Starting server processes ... ");
		int port = 10000;
		for (RepoInformation r : repoInformation) {
			if (r.repoLoc == null)
				continue;
			startSparqlServer(r.repoLoc, port++);
			runningServers++;
		}
				
		log.info("Waiting for " + extraWait + " ms to give server time for initialization");
		System.gc();
		Thread.sleep(extraWait);
		
		log.debug("Loading repositories from scratch.");
		sailRepo = loadRepository();
		conn = sailRepo.getConnection();
		log.debug("Reinitialize done.");
	}
	
	/**
	 * Method to load repositories, used in reinitialize.
	 * 
	 * Subclasses can overwrite this
	 * @return
	 * @throws Exception
	 */
	protected SailRepository loadRepository() throws Exception {
		return SesameRepositoryLoader.loadRepositories(new VoidReportStream());
	}
	
	protected boolean deleteFolder(File folder) {
		for (File f : folder.listFiles()) {
			if (f.isDirectory()) {
				if (!deleteFolder(f))
					return false;
				continue;
			}
			if (!f.delete())
				return false;
		}
		return folder.delete();
	}
	
	
	/**
	 * Sparql servers write file %pid%.pid in root directory.
	 * Kill all processes that are still running.
	 * 
	 */
	protected void checkAndKillServers() throws Exception {
		File cd = new File(".");
		log.info("Checking dir " + cd.getAbsolutePath() + " for *.pid files.");
		for (File f : cd.listFiles()) {
			if (!f.getName().endsWith(".pid"))
				continue;
			String pid = f.getName().replaceAll(".pid", "");
			kill(pid);
			f.delete();
		}
		// give the processes a chance to finish
		Thread.sleep(2000);
	}
	
	
	
	protected Process startSparqlServer(File repoLoc, int port) throws Exception {
		log.info("Starting endpoint for repository " + repoLoc.getAbsolutePath() + " on port " + port);
		String command = "cmd /c start startSparqlEndpoint.bat";
		Process p = Runtime.getRuntime().exec(command + " \"" + repoLoc.getAbsolutePath() + "\" " + port);
		return p;
	}	
	
	protected void kill(String pid) throws Exception {
		log.info("Killing process with id " + pid);
		String command = "lib\\pskill /accepteula " + pid;
		Runtime.getRuntime().exec(command);
	}
	
	
	protected List<RepoInformation> getRepoInformation() throws RDFParseException, RDFHandlerException, IOException {
		List<RepoInformation> res = new ArrayList<RepoInformation>();
				
		final Graph graph = new GraphImpl();
		final String dataConfig = Config.getConfig().getDataConfig();
		RDFParser parser = Rio.createParser(RDFFormat.N3);
		RDFHandler handler = new SimpleRDFHandler(graph);		
		parser.setRDFHandler(handler);
		
		parser.parse(new FileReader(dataConfig), "http://fluidops.org/config#");
		Iterator<Statement> iter = graph.match(null, new URIImpl("http://fluidops.org/config#store"), null);
		
		while (iter.hasNext()){
			String id = null;
			File repoLoc = null;
			String url = null;
			Statement s = iter.next();
			
			// id is the subject:
			id = s.getSubject().stringValue();
						
			Iterator<Statement> tmpIter;
			
			// repoLoc is http://fluidops.org/config#localRepoLoc
			tmpIter = graph.match(s.getSubject(), new URIImpl("http://fluidops.org/config#localRepoLoc"), null);
			if (tmpIter.hasNext()) {
				repoLoc = new File(tmpIter.next().getObject().stringValue());
				log.debug("Found local repoLoc " + repoLoc.getPath());
			}
			
			// url is http://fluidops.org/config#SPARQLEndpoint
			tmpIter = graph.match(s.getSubject(), new URIImpl("http://fluidops.org/config#SPARQLEndpoint"), null);
			if (tmpIter.hasNext()) {
				url = tmpIter.next().getObject().stringValue();
			}
			
			res.add( new RepoInformation(id, url, repoLoc) );
		}
		return res;
	}
	
	public class RepoInformation {
		public String id;
		public String url;
		public File repoLoc;
		public RepoInformation(String id, String url, File repoLoc) {
			super();
			this.id = id;
			this.url = url;
			this.repoLoc = repoLoc;
		}
	}
	
	protected class SimpleRDFHandler implements RDFHandler {

		protected final Graph graph;
				
		public SimpleRDFHandler(Graph graph) {
			super();
			this.graph = graph;
		}

		@Override
		public void endRDF() throws RDFHandlerException {						
		}

		@Override
		public void handleComment(String arg0){
		}

		@Override
		public void handleNamespace(String arg0, String arg1){
		}

		@Override
		public void handleStatement(Statement arg0)
				throws RDFHandlerException {
			graph.add(arg0);
		}

		@Override
		public void startRDF() throws RDFHandlerException {
		}
		
	};
}