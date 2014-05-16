package uk.co.revsys.objectology.serialiser;

public class ObjectMapper {

	private final SerialiserFactory serialiserFactory;
	private final DeserialiserFactory deserialiserFactory;

	public ObjectMapper(SerialiserFactory serialiserFactory, DeserialiserFactory deserialiserFactory) {
		this.serialiserFactory = serialiserFactory;
		this.deserialiserFactory = deserialiserFactory;
	}
	
	public String serialise(Object object, Object... args) throws SerialiserException{
		return getSerialiser(object.getClass()).serialise(this, object, args);
	}
	
	public <O extends Object> O deserialise(String source, Class<? extends O> type, Object... args) throws DeserialiserException{
		return (O) getDeserialiser(type).deserialise(this, source, args);
	}

	public Serialiser getSerialiser(Class type) throws SerialiserException{
		Serialiser serialiser = serialiserFactory.getInstance(type);
		if(serialiser == null){
			throw new SerialiserException("No serialiser found for " + type.getName());
		}
		return serialiser;
	}
	
	public Deserialiser getDeserialiser(Class type) throws DeserialiserException{
		Deserialiser deserialiser = deserialiserFactory.getInstance(type);
		if(deserialiser == null){
			throw new DeserialiserException("No deserialiser found for " + type.getName());
		}
		return deserialiser;
	}
	
	public Deserialiser getDeserialiser(String alias) throws DeserialiserException{
		Deserialiser deserialiser = deserialiserFactory.getInstance(alias);
		if(deserialiser == null){
			throw new DeserialiserException("No deserialiser found for " + alias);
		}
		return deserialiser;
	}
	
}
