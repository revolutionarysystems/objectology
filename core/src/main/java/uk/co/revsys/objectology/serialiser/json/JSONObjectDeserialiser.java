package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;

public abstract class JSONObjectDeserialiser<O extends Object> extends JSONDeserialiser<O, JSONObject>{

	@Override
	protected JSONObject convertStringToJSON(String source) {
		return new JSONObject(source);
	}

}
