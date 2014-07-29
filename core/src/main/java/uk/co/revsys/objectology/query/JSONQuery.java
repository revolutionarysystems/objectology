package uk.co.revsys.objectology.query;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONQuery extends JSONObject implements Query{

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
	public String toQueryString() {
		return toString();
	}

}
