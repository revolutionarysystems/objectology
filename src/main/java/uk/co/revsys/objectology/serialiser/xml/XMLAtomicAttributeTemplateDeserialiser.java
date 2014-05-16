package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.Node;
import uk.co.revsys.objectology.model.template.AtomicAttributeTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class XMLAtomicAttributeTemplateDeserialiser<T extends AtomicAttributeTemplate> extends XMLAttributeTemplateDeserialiser<T>{

	private Class<? extends T> type;

	public XMLAtomicAttributeTemplateDeserialiser(Class<? extends T> type) {
		this.type = type;
	}
	
	@Override
	public T deserialise(ObjectMapper objectMapper, Node source, Object... args) throws DeserialiserException {
		try {
			T instance = type.newInstance();
			return instance;
		} catch (InstantiationException ex) {
			throw new DeserialiserException(ex);
		} catch (IllegalAccessException ex) {
			throw new DeserialiserException(ex);
		}
	}

}
