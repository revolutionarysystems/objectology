package uk.co.revsys.objectology.query;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONQuery extends JSONObject implements Query{

    private int offset = -1;
    private int limit = -1;
    private List<QuerySortingRule> sortingRules = new LinkedList<QuerySortingRule>();
    
	public JSONQuery() {
	}

	public JSONQuery(Map map) {
		super(map);
	}

	public JSONQuery(Object bean) {
		super(bean);
	}

	public JSONQuery(String source) throws JSONException {
		super(source);
	}
    
    public JSONQuery(String key, String value){
        super();
        put(key, value);
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public List<QuerySortingRule> getSortingRules() {
        return sortingRules;
    }

    @Override
    public void setSortingRules(List<QuerySortingRule> sortingRules) {
        this.sortingRules = sortingRules;
    }
	
	@Override
	public String toQueryString() {
		return toString();
	}

}
