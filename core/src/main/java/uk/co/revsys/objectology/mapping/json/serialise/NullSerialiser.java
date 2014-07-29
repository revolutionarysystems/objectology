package uk.co.revsys.objectology.mapping.json.serialise;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import uk.co.revsys.objectology.mapping.json.JSONNullType;

public class NullSerialiser extends JsonSerializer<Object>{

    @Override
    public void serialize(Object t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        System.out.println("null serialiser");
        JSONNullType jsonNullType = t.getClass().getAnnotation(JSONNullType.class);
        jg.writeRaw(":" + jsonNullType.value());
    }

}
