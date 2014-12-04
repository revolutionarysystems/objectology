package uk.co.revsys.objectology.view.definition.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import uk.co.revsys.objectology.view.definition.rule.ViewDefinitionRule;

public class ViewDefinitionRuleDeserialiser extends JsonDeserializer<ViewDefinitionRule>{

    private Map<String, Class<? extends ViewDefinitionRule>> mappings;

    public ViewDefinitionRuleDeserialiser(Map<String, Class<? extends ViewDefinitionRule>> mappings) {
        this.mappings = mappings;
    }

    @Override
    public ViewDefinitionRule deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode root = jp.getCodec().readTree(jp);
        String type = root.get("type").asText();
        Class clazz = mappings.get(type);
        if(clazz == null){
            return null;
        }
        return (ViewDefinitionRule) mapper.readValue(root.toString(), clazz);
    }
    
}
