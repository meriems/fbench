package org.semanticweb.fbench.report;

import java.util.List;

import org.semanticweb.fbench.query.Query;
import org.semanticweb.fbench.query.QueryType;



/**
 * Combined report stream for simple and cvs reporting. Uses the delegate pattern. 
 * 
 * @see SimpleReportStream
 * @see CvsReportStream
 * 
 * @author as
 *
 */
public class CombinedReportStream implements ReportStream {

	
	protected SimpleReportStream simple = new SimpleReportStream();
	protected CvsReportStream cvs = new CvsReportStream();
	
	public void beginEvaluation(String dataConfig, List<QueryType> querySet,
			int numberOfQueries, int numberOfRuns) {
		cvs.beginEvaluation(dataConfig, querySet, numberOfQueries, numberOfRuns);
		simple.beginEvaluation(dataConfig, querySet, numberOfQueries, numberOfRuns);
	}
	public void beginQueryEvaluation(Query query, int run) {
		cvs.beginQueryEvaluation(query, run);
		simple.beginQueryEvaluation(query, run);
	}
	public void beginRun(int run, int totalNumberOfRuns) {
		cvs.beginRun(run, totalNumberOfRuns);
		simple.beginRun(run, totalNumberOfRuns);
	}
	public void close() throws Exception {
		cvs.close();
		simple.close();
	}
	public void endEvaluation(long duration) {
		cvs.endEvaluation(duration);
		simple.endEvaluation(duration);
	}
	public void endQueryEvaluation(Query query, int run, long duration,
			int numberOfResults) {
		cvs.endQueryEvaluation(query, run, duration, numberOfResults);
		simple.endQueryEvaluation(query, run, duration, numberOfResults);
	}
	public void endRun(int run, int totalNumberOfRuns, long duration) {
		cvs.endRun(run, totalNumberOfRuns, duration);
		simple.endRun(run, totalNumberOfRuns, duration);
	}
	public void error(String errorMsg, Exception ex) {
		cvs.error(errorMsg, ex);
		simple.error(errorMsg, ex);
	}
	public void initializationBegin() {
		cvs.initializationBegin();
		simple.initializationBegin();
	}
	public void initializationEnd(long duration) {
		cvs.initializationEnd(duration);
		simple.initializationEnd(duration);
	}
	public void open() throws Exception {
		cvs.open();
		simple.open();
	}
	public void reportDatasetLoadTime(String name, String location, String type, long duration) {
		cvs.reportDatasetLoadTime(name, location, type, duration);
		simple.reportDatasetLoadTime(name, location, type, duration);
	}
}
