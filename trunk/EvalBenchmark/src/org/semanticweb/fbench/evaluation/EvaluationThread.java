package org.semanticweb.fbench.evaluation;

import org.apache.log4j.Logger;
import org.semanticweb.fbench.query.Query;
import org.semanticweb.fbench.report.EarlyResultsMonitor;
import org.semanticweb.fbench.report.ReportStream;



/**
 * Helper thread to allow for timeout handling while executing queries
 * 
 * @author as
 */
public class EvaluationThread extends Thread {
	
	public static Logger log = Logger.getLogger(EvaluationThread.class);
	
	protected Evaluation evaluator;
	protected Query query;
	protected ReportStream report;
	protected EarlyResultsMonitor earlyResults;
	protected int run;
	
	private boolean finished;
		
	public EvaluationThread(Evaluation evaluator, Query query, ReportStream report, EarlyResultsMonitor earlyResults, int run) {
		super();
		this.evaluator = evaluator;
		this.query = query;
		this.report = report;
		this.run = run;
		this.earlyResults = earlyResults;
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
			earlyResults.nextQuery(query, start);
			int numberOfResults = evaluator.runQuery(query);
			long duration = System.currentTimeMillis() - start;
			report.endQueryEvaluation(query, run, duration, numberOfResults);
		} catch (IllegalMonitorStateException e) { 
			// reporting is done in evaluation (finished is till false)
			log.info("Execution of query " + query.getIdentifier() + " resulted in timeout.");
			return;
		} catch (Exception e) {
			report.endQueryEvaluation(query, run, -1, -1);
			log.error("Error executing query " + query.getIdentifier() + " (" + e.getClass().getSimpleName() + "): " + e.getMessage());
			log.debug("Exception details:", e);
		}
		this.finished = true;
		synchronized (Evaluation.class) {
			Evaluation.class.notify();	
		}
	}

}
