package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import uk.co.revsys.objectology.mapping.json.JsonObjectMapper;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.service.OlogyInstanceBundle;

public class OlogyInstanceBundleDeserialiser extends JsonDeserializer<OlogyInstanceBundle>{

    @Override
    public OlogyInstanceBundle deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonObjectMapper mapper = (JsonObjectMapper) jp.getCodec();
        OlogyInstanceBundle bundle = new OlogyInstanceBundle();
        OlogyInstance instance = mapper.reader(OlogyInstance.class).withAttribute("instanceBundle", bundle).readValue(jp);
        bundle.setInstance(instance);
        return bundle;
    }

}
