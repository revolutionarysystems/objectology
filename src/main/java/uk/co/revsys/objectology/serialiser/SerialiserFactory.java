package uk.co.revsys.objectology.serialiser;

import java.util.Map;

public class SerialiserFactory extends AbstractFactory<Serialiser>{

	public SerialiserFactory(Map<Class, Serialiser> objectMap, Map aliasMap) {
		super(objectMap, aliasMap);
	}
	
}
