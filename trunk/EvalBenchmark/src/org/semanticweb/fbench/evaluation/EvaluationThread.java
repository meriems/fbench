package org.semanticweb.fbench.evaluation;

import org.semanticweb.fbench.query.Query;
import org.semanticweb.fbench.report.ReportStream;



/**
 * Helper thread to allow for timeout handling while executing queries
 * 
 * @author as
 */
public class EvaluationThread extends Thread {
	
	protected Evaluation evaluator;
	protected Query query;
	protected ReportStream report;
	protected int run;
	
	private boolean finished;
		
	public EvaluationThread(Evaluation evaluator, Query query, ReportStream report, int run) {
		super();
		this.evaluator = evaluator;
		this.query = query;
		this.report = report;
		this.run = run;
		this.finished = false;
	}
	
	public boolean isFinished() {
		return this.finished;
	}
	
	@Override
	public void run() {
		try {
			report.beginQueryEvaluation(query, run);
			long start = System.currentTimeMillis();
			int numberOfResults = evaluator.runQuery(query);
			long duration = System.currentTimeMillis() - start;
			report.endQueryEvaluation(query, run, duration, numberOfResults);
		} catch (Exception e) {
			report.endQueryEvaluation(query, run, -1, -1);
			report.error("Error executing query " + query.getIdentifier(), e);
		}
		this.finished = true;
	}

}
