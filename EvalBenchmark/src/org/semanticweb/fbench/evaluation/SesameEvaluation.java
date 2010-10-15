package org.semanticweb.fbench.evaluation;

import java.util.Iterator;

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
		System.out.println("Performing Sesame Initialization...");
		
		sailRepo = SesameRepositoryLoader.loadRepositories(report);
		if (!Config.getConfig().isFill())
			conn = sailRepo.getConnection();
		
		System.out.println("Sesame Repository successfully initialized.");
	}

	@Override
	public int runQuery(Query q) throws Exception {
		TupleQuery query = conn.prepareTupleQuery(QueryLanguage.SPARQL, q.getQuery());
		TupleQueryResult res = (TupleQueryResult) query.evaluate();
		int resCounter = 0;
		while(res.hasNext()){
			BindingSet bindings = res.next();
			Iterator<String> stringIter = bindings.getBindingNames().iterator();
			while (stringIter.hasNext()){
				stringIter.next();
			}
			resCounter++;
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
			Iterator<String> stringIter = bindings.getBindingNames().iterator();
			String result = "";
			while (stringIter.hasNext()){
				String tmp = stringIter.next();
				result = tmp + ": " + bindings.getValue(tmp) + " | " + result;
			}
			if (showResult){
				// TODO use logging
				System.out.println(result);
			}
			
			resCounter++;
		}
		return resCounter;
	}

}
