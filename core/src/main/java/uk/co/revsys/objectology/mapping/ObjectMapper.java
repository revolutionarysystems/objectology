package uk.co.revsys.objectology.mapping;

import java.util.Map;

public interface ObjectMapper {

    public String serialise(Object object) throws SerialiserException;
    
    public String serialise(Object object, Map<String, Object> parameters) throws SerialiserException;
    
    public <O> O deserialise(String source, Class<? extends O> type) throws DeserialiserException;
    
    public <O> O deserialise(String source, Class<? extends O> type, Map<String, Object> parameters) throws DeserialiserException;
    
}
