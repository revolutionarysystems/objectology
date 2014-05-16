package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONArray;

public abstract class JSONArrayDeserialiser<O extends Object> extends JSONDeserialiser<O, JSONArray>{

	@Override
	protected JSONArray convertStringToJSON(String source) {
		return new JSONArray(source);
	}

}
