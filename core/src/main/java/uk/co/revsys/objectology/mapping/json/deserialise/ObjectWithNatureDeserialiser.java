package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import uk.co.revsys.objectology.mapping.NatureMap;
import uk.co.revsys.objectology.model.ObjectWithNature;

public class ObjectWithNatureDeserialiser<O extends ObjectWithNature> extends JsonDeserializer<O> {

    @Override
    public O deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode root = jp.getCodec().readTree(jp);
        String nature = root.get("nature").asText();
        Class<? extends ObjectWithNature> templateType = NatureMap.getTemplateType(nature);
        ObjectWithNature object = mapper.readValue(root.toString(), templateType);
        return (O)object;
    }

}
