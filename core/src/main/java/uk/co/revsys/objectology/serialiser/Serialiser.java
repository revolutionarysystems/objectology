package uk.co.revsys.objectology.serialiser;

public interface Serialiser<O extends Object> {

	public String serialise(ObjectMapper objectMapper, O object, Object... args) throws SerialiserException;
	
}
