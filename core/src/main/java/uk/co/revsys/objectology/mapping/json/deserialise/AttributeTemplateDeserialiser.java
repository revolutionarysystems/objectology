package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.serialiser.NatureMap;

public class AttributeTemplateDeserialiser extends JsonDeserializer<AttributeTemplate> {

    @Override
    public AttributeTemplate deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode root = jp.getCodec().readTree(jp);
        System.out.println("AttributeTemplate: " + root.toString());
        String nature = root.get("nature").asText();
        System.out.println("nature = " + nature);
        Class<? extends AttributeTemplate> templateType = NatureMap.getTemplateType(nature);
        System.out.println("templateType = " + templateType);
        AttributeTemplate attributeTemplate = mapper.readValue(root.toString(), templateType);
        System.out.println("attributeTemplate = " + attributeTemplate);
        return attributeTemplate;
    }

}
