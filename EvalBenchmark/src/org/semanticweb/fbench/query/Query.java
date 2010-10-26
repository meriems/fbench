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
		return type.getFileName() + "_" + number;
	}
	
	@Override
	public String toString() {
		return query;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Query other = (Query) obj;
		if (number != other.number)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
