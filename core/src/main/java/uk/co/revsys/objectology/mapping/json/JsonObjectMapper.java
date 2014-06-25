package uk.co.revsys.objectology.mapping.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.IOException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.mapping.SerialiserException;

public class JsonObjectMapper extends ContextualObjectMapper implements ObjectMapper{

    public JsonObjectMapper() {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    @Override
    public String serialise(Object object) throws SerialiserException {
        try {
            return writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new SerialiserException(ex);
        }
    }

    @Override
    public <O> O deserialise(String source, Class<? extends O> type) throws DeserialiserException {
        try {
            return readValue(source, type);
        } catch (IOException ex) {
            throw new DeserialiserException(ex);
        }
    }

}
