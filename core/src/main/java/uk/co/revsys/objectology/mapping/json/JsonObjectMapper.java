package uk.co.revsys.objectology.mapping.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.mapping.SerialiserException;

public class JsonObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper implements ObjectMapper{

    public JsonObjectMapper() {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    @Override
    public String serialise(Object object) throws SerialiserException {
        return serialise(object, new HashMap<String, Object>());
    }

    @Override
    public String serialise(Object object, Map<String, Object> parameters) throws SerialiserException {
        try {
            if(object==null){
                return "null";
            }
            ObjectWriter writer = writerWithType(object.getClass());
            for(Entry<String, Object> entry: parameters.entrySet()){
                writer = writer.withAttribute(entry.getKey(), entry.getValue());
            }
            return writer.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new SerialiserException(ex);
        }
    }

    @Override
    public <O> O deserialise(String source, Class<? extends O> type) throws DeserialiserException {
        return deserialise(source, type, new HashMap<String, Object>());
    }

    @Override
    public <O> O deserialise(String source, Class<? extends O> type, Map<String, Object> parameters) throws DeserialiserException {
        try {
            ObjectReader reader = reader(type);
            for(Entry<String, Object> entry: parameters.entrySet()){
                reader = reader.withAttribute(entry.getKey(), entry.getValue());
            }
            return reader.readValue(source);
        }catch(DeserialiserException ex){
            throw ex;
        }catch (IOException ex) {
            throw new DeserialiserException(ex);
        }
    }

}
