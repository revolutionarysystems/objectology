package uk.co.revsys.objectology.serialiser;

import java.util.Map;

public abstract class AbstractFactory<O extends Object> {

	private final Map<Class, O> objectMap;
	private final Map<String, Class> aliasMap;

	public AbstractFactory(Map<Class, O> objectMap, Map<String, Class> aliasMap) {
		this.objectMap = objectMap;
		this.aliasMap = aliasMap;
	}
	
	public O getInstance(Class type){
		return objectMap.get(type);
	}
	
	public O getInstance(String alias){
		return objectMap.get(aliasMap.get(alias));
	}
	
}
