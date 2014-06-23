package uk.co.revsys.objectology.mapping.json.serialise;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.serialiser.NatureMap;

public class NatureSerialiser extends JsonSerializer<Class<? extends Attribute>>{

    @Override
    public void serialize(Class<? extends Attribute> t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeString(NatureMap.getInstanceNature(t));
    }

}
