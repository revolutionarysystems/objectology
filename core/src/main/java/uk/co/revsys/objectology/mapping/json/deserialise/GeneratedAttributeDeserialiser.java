package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.model.instance.GeneratedAttribute;

public class GeneratedAttributeDeserialiser<G extends GeneratedAttribute> extends JsonDeserializer<G>{

    private final Class<G> type;

    public GeneratedAttributeDeserialiser(Class<G> type) {
        this.type = type;
    }
    
    @Override
    public G deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        try {
            return type.newInstance();
        } catch (InstantiationException ex) {
            throw new DeserialiserException(ex);
        } catch (IllegalAccessException ex) {
            throw new DeserialiserException(ex);
        }
    }

}
