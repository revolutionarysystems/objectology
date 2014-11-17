package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.model.instance.BooleanValue;

public class BooleanValueDeserialiser extends JsonDeserializer<BooleanValue>{

    public BooleanValueDeserialiser() {
    }

    @Override
    public BooleanValue deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        try {
            boolean value = jp.getCodec().readValue(jp, Boolean.class);
            return new BooleanValue(value);
        } catch (ValidationException ex) {
            throw new DeserialiserException(ex);
        }
    }

}
