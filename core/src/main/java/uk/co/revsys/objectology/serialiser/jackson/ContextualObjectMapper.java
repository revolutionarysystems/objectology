package uk.co.revsys.objectology.serialiser.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class ContextualObjectMapper extends ObjectMapper {

    private ThreadContext threadContext = new ThreadContext();

    public ContextualObjectMapper() {
    }

    public ThreadContext getThreadContext() {
        return threadContext;
    }

    @Override
    public <T> T readValue(String string, Class<T> type) throws IOException, JsonParseException, JsonMappingException {
        return readValue(string, type, false);
    }

    public <T> T readValue(String string, Class<T> type, boolean preserveThreadContext) throws IOException, JsonParseException, JsonMappingException {
        if (!preserveThreadContext) {
            threadContext.clear();
        }
        return super.readValue(string, type);
    }

}
