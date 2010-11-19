package org.semanticweb.fbench.evaluation;

import org.apache.log4j.Logger;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.sail.SailRepositoryConnection;
import org.semanticweb.fbench.Config;
import org.semanticweb.fbench.query.Query;



/**
 * Implementation for query evaluation based on original Sesame Build
 * 
 * @author as
 */
public class SesameEvaluation extends Evaluation {

	public static Logger log = Logger.getLogger(SesameEvaluation.class);
	
	protected SailRepository sailRepo;
	protected SailRepositoryConnection conn;
	
	public SesameEvaluation() {
		super();
	}
	
	@Override
	public void finish() throws Exception {
		conn.close();	
		sailRepo.shutDown();
	}

	@Override
	public void initialize() throws Exception {
		log.info("Performing Sesame Initialization...");
		
		sailRepo = SesameRepositoryLoader.loadRepositories(report);
		if (!Config.getConfig().isFill())
			conn = sailRepo.getConnection();
		
		log.info("Sesame Repository successfully initialized.");
	}

	@Override
	public int runQuery(Query q) throws Exception {
		TupleQuery query = conn.prepareTupleQuery(QueryLanguage.SPARQL, q.getQuery());
		TupleQueryResult res = (TupleQueryResult) query.evaluate();
		int resCounter = 0;
		while(res.hasNext()){
			BindingSet bindings = res.next();
			resCounter++;
			earlyResults.handleResult(bindings, resCounter);			
		}
		return resCounter;
	}

	@Override
	public int runQueryDebug(Query q, boolean showResult) throws Exception {
		TupleQuery query = conn.prepareTupleQuery(QueryLanguage.SPARQL, q.getQuery());
		TupleQueryResult res = (TupleQueryResult) query.evaluate();
		int resCounter = 0;
		while(res.hasNext()){
			BindingSet bindings = res.next();
			if (showResult){
				// TODO use logging
				System.out.println(bindings);
			}
			
			resCounter++;
			earlyResults.handleResult(bindings, resCounter);	
		}
		return resCounter;
	}

}
