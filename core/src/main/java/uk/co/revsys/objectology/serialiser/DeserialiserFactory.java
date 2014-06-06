package uk.co.revsys.objectology.serialiser;

import java.util.Map;

public class DeserialiserFactory extends AbstractFactory<Deserialiser>{

	public DeserialiserFactory(Map<Class, Deserialiser> objectMap, Map aliasMap) {
		super(objectMap, aliasMap);
	}

}
