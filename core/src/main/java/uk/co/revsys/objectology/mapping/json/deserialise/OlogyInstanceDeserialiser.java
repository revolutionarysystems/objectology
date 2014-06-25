package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.Map.Entry;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.mapping.json.ContextualObjectMapper;
import uk.co.revsys.objectology.service.OlogyObjectServiceFactory;

public class OlogyInstanceDeserialiser extends JsonDeserializer<OlogyInstance> {

    @Override
    public OlogyInstance deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        OlogyInstance instance = new OlogyInstance();
        ContextualObjectMapper mapper = (ContextualObjectMapper) jp.getCodec();
        ObjectNode root = mapper.readTree(jp);
        System.out.println("root = " + root);
        if (root.has("id")) {
            instance.setId(root.get("id").asText());
        }
        if (root.has("name")) {
            instance.setName(root.get("name").asText());
        }
        OlogyTemplate template = (OlogyTemplate) mapper.getThreadContext().get("template");
        if (template == null) {
            String templateId = null;
            if (root.has("template")) {
                templateId = root.get("template").asText();
            } else if (root.has("templateId")) {
                templateId = root.get("templateId").asText();
            }
            if (templateId != null) {
                try {
                    template = OlogyObjectServiceFactory.getOlogyTemplateService().findById(templateId);
                } catch (DaoException ex) {
                    throw new IOException(ex);
                }
            } else {
                try {
                    template = OlogyObjectServiceFactory.getOlogyTemplateService().findByName(root.get("templateName").asText());
                } catch (DaoException ex) {
                    throw new IOException(ex);
                }
            }
        }
        instance.setTemplate(template);
        for (Entry<String, AttributeTemplate> attributeTemplate : template.getAttributeTemplates().entrySet()) {
            String attributeName = attributeTemplate.getKey();
            String attributeJson = "null";
            if (root.has(attributeName)) {
                attributeJson = root.get(attributeName).toString();
            }
            System.out.println("attributeName = " + attributeName);
            System.out.println("attributeJson = " + attributeJson);
            System.out.println("attributeType = " + attributeTemplate.getValue().getAttributeType());
            mapper.getThreadContext().set("template", attributeTemplate.getValue());
            Attribute attribute = (Attribute) mapper.readValue(attributeJson, attributeTemplate.getValue().getAttributeType(), true);
            System.out.println("attribute = " + attribute);
            attribute.setTemplate(attributeTemplate.getValue());
            instance.setAttribute(attributeName, attribute);
        }
        return instance;
    }

}
