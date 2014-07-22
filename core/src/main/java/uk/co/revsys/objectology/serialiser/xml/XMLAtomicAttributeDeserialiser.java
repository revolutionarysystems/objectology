package uk.co.revsys.objectology.serialiser.xml;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Node;
import uk.co.revsys.objectology.model.instance.AtomicAttribute;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;

public class XMLAtomicAttributeDeserialiser<A extends AtomicAttribute> extends XMLAttributeDeserialiser<A> {

    private final Class<? extends A> type;

    public XMLAtomicAttributeDeserialiser(Class<? extends A> type) {
        this.type = type;
    }

    @Override
    public A deserialise(XMLObjectMapper objectMapper, Node source, Object... args) throws DeserialiserException {
        try {
            if (source == null) {
                return null;
            }
            A instance = type.newInstance();            
            instance.setValueFromString(source.getText());
            instance.setTemplate((AttributeTemplate) args[0]);
            return instance;
        } catch (InstantiationException ex) {
            throw new DeserialiserException(ex);
        } catch (IllegalAccessException ex) {
            throw new DeserialiserException(ex);
        } catch (ParseException ex) {
            throw new DeserialiserException(ex);
        }
    }

}
