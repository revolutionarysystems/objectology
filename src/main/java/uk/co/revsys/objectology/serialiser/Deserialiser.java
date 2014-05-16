package uk.co.revsys.objectology.serialiser;

public interface Deserialiser<O extends Object> {

	public O deserialise(ObjectMapper objectMapper, String source, Object... args) throws DeserialiserException;
	
}
