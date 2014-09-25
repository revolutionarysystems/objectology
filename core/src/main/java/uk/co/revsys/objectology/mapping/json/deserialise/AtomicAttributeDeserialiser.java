package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.model.instance.AtomicAttribute;
import uk.co.revsys.objectology.model.template.AttributeTemplate;

public class AtomicAttributeDeserialiser extends JsonDeserializer<AtomicAttribute>{

    @Override
    public AtomicAttribute deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        try {
            AttributeTemplate template = (AttributeTemplate) dc.getAttribute("template");
            String value = jp.getValueAsString();
            Class attributeType = template.getAttributeType();
            AtomicAttribute attribute = (AtomicAttribute) attributeType.getConstructor(template.getClass(), String.class).newInstance(template, value);
            return attribute;
        } catch (NoSuchMethodException ex) {
            throw new DeserialiserException(ex);
        } catch (SecurityException ex) {
            throw new DeserialiserException(ex);
        } catch (InstantiationException ex) {
            throw new DeserialiserException(ex);
        } catch (IllegalAccessException ex) {
            throw new DeserialiserException(ex);
        } catch (IllegalArgumentException ex) {
            throw new DeserialiserException(ex);
        } catch (InvocationTargetException ex) {
            throw new DeserialiserException(ex);
        }
    }

}
