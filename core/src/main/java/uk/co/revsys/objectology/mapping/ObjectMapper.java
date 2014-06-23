package uk.co.revsys.objectology.mapping;

public interface ObjectMapper {

    public String serialise(Object object) throws SerialiserException;
    
    public <O> O deserialise(String source, Class<? extends O> type) throws DeserialiserException;
    
}