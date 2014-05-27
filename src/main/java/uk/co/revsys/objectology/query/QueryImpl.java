package uk.co.revsys.objectology.query;

public class QueryImpl implements Query{

	private String queryString;

	public QueryImpl() {
	}

	public QueryImpl(String queryString) {
		this.queryString = queryString;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	@Override
	public String toQueryString() {
		return queryString;
	}
	
}
