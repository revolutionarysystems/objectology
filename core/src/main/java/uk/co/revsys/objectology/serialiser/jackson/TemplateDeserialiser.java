package uk.co.revsys.objectology.serialiser.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import uk.co.revsys.objectology.model.template.OlogyTemplate;

public class TemplateDeserialiser extends JsonDeserializer<OlogyTemplate> {

    @Override
    public OlogyTemplate deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        OlogyTemplate template = new OlogyTemplate();
        JsonNode root = jp.getCodec().readTree(jp);
        if (root.has("id")) {
            template.setId(root.get("id").asText());
        }
        if (root.has("name")) {
            template.setName(root.get("name").asText());
        }
        template.setType(root.get("type").asText());
        JsonNode attributes = root.get("attributes");
        for(JsonNode attribute: attributes){
            
        }
        return template;
    }

}
