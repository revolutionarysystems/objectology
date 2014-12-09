package uk.co.revsys.objectology.query;

import java.util.List;

public interface Query {
    
    public void setOffset(int offset);
    
    public int getOffset();
    
    public void setLimit(int limit);
    
    public int getLimit();
    
    public void setSortingRules(List<QuerySortingRule> sortingRules);
    
    public List<QuerySortingRule> getSortingRules();
    
	public String toQueryString();
	
}
