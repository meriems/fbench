package org.semanticweb.fbench.query;


/**
 * Data class for a SPARQL query
 * 
 * @author as
 *
 */
public class Query {

	
	protected String query;				// the query itself
	protected QueryType type;			// the type of the query (defines the file)
	protected int number;				// the number of this query in queries file
	
	public Query(String query, QueryType type, int number) {
		super();
		this.query = query;
		this.type = type;
		this.number = number;
	}

	public String getQuery() {
		return query;
	}

	public QueryType getType() {
		return type;
	}

	public int getNumber() {
		return number;
	}
	
	public String getIdentifier() {
		return type.getFileName() + ":" + number;
	}
	
	@Override
	public String toString() {
		return query;
	}
	
}
