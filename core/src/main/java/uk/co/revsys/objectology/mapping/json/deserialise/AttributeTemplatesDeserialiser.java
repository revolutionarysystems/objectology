package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import uk.co.revsys.objectology.model.template.AttributeTemplate;

public class AttributeTemplatesDeserialiser extends JsonDeserializer<Map<String, AttributeTemplate>> {

    @Override
    public Map<String, AttributeTemplate> deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        ObjectNode root = jp.getCodec().readTree(jp);
        Iterator<Map.Entry<String, JsonNode>> iterator = root.fields();
        Map<String, AttributeTemplate> templates = new HashMap<String, AttributeTemplate>();
        while(iterator.hasNext()){
            Map.Entry<String, JsonNode> next = iterator.next();
            String attributeName = next.getKey();
            JsonNode node = next.getValue();
            if (node != null) {
                AttributeTemplate attributeTemplate = mapper.readValue(node.toString(), AttributeTemplate.class);
                templates.put(attributeName, attributeTemplate);
            }
        }
        return templates;
    }

}
