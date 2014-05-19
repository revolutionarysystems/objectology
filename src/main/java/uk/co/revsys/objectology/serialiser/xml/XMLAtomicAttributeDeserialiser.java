package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.Node;
import uk.co.revsys.objectology.model.instance.AtomicAttribute;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class XMLAtomicAttributeDeserialiser<A extends AtomicAttribute> extends XMLAttributeDeserialiser<A>{

	private final Class<? extends A> type;

	public XMLAtomicAttributeDeserialiser(Class<? extends A> type) {
		this.type = type;
	}
	
	@Override
	public A deserialise(ObjectMapper objectMapper, Node source, Object... args) throws DeserialiserException {
		try {
			A instance = type.newInstance();
			instance.setValue(source.getText());
			instance.setTemplate((AttributeTemplate)args[0]);
			return instance;
		} catch (InstantiationException ex) {
			throw new DeserialiserException(ex);
		} catch (IllegalAccessException ex) {
			throw new DeserialiserException(ex);
		}
	}

}
