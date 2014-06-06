package uk.co.revsys.objectology.serialiser.json;

public abstract class JSONStringDeserialiser<O extends Object> extends JSONDeserialiser<Object, String>{

	@Override
	protected String convertStringToJSON(String source) {
		return source;
	}

}
