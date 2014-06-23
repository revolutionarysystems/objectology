package uk.co.revsys.objectology.mapping.xml;

import uk.co.revsys.objectology.mapping.SerialiserException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.serialiser.Deserialiser;
import uk.co.revsys.objectology.serialiser.DeserialiserFactory;
import uk.co.revsys.objectology.serialiser.Serialiser;
import uk.co.revsys.objectology.serialiser.SerialiserFactory;

public class XMLObjectMapper implements ObjectMapper{

	private final SerialiserFactory serialiserFactory;
	private final DeserialiserFactory deserialiserFactory;

	public XMLObjectMapper(SerialiserFactory serialiserFactory, DeserialiserFactory deserialiserFactory) {
		this.serialiserFactory = serialiserFactory;
		this.deserialiserFactory = deserialiserFactory;
	}

    @Override
    public String serialise(Object object) throws SerialiserException {
        return serialise(object, new Object[0]);
    }
	
	public String serialise(Object object, Object... args) throws SerialiserException{
		return getSerialiser(object.getClass()).serialise(this, object, args);
	}

    @Override
    public <O> O deserialise(String source, Class<? extends O> type) throws DeserialiserException {
        return deserialise(source, type, new Object[0]);
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
