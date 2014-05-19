package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.Node;
import uk.co.revsys.objectology.model.instance.Attribute;
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
			System.out.println("type = " + type.getName());
			T instance = type.newInstance();
			XMLAttributeDeserialiser valueDeserialiser = (XMLAttributeDeserialiser) objectMapper.getDeserialiser(instance.getAttributeType());
			Attribute value = (Attribute) valueDeserialiser.deserialise(objectMapper, source, instance);
			instance.setValue(value);
			return instance;
		} catch (InstantiationException ex) {
			throw new DeserialiserException(ex);
		} catch (IllegalAccessException ex) {
			throw new DeserialiserException(ex);
		}
	}

}
