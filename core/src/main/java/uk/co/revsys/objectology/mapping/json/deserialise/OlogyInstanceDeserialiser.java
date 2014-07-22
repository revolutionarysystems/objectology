package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.Map.Entry;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.json.JsonObjectMapper;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.SequenceTemplate;
import uk.co.revsys.objectology.service.OlogyObjectServiceFactory;

public class OlogyInstanceDeserialiser extends JsonDeserializer<OlogyInstance> {

    @Override
    public OlogyInstance deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        OlogyInstance instance = new OlogyInstance();
        JsonObjectMapper mapper = (JsonObjectMapper) jp.getCodec();
        ObjectNode root = mapper.readTree(jp);
        if (root.has("id")) {
            instance.setId(root.get("id").asText());
        }
        if (root.has("name")) {
            instance.setName(root.get("name").asText());
        }
        OlogyTemplate template = (OlogyTemplate) dc.getAttribute("template");
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
                    if (template == null) {
                        throw new DeserialiserException("Template with id " + templateId + " not found");
                    }
                } catch (DaoException ex) {
                    throw new DeserialiserException(ex);
                }
            } else {
                try {
                    String templateName = root.get("templateName").asText();
                    template = OlogyObjectServiceFactory.getOlogyTemplateService().findByName(templateName);
                    if (template == null) {
                        throw new DeserialiserException("Template with name " + templateName + " not found");
                    }
                } catch (DaoException ex) {
                    throw new DeserialiserException(ex);
                }
            }
        }
        if (template == null) {
            throw new DeserialiserException("Template not found");
        }
        instance.setTemplate(template);
        for (Entry<String, AttributeTemplate> attributeTemplate : template.getAttributeTemplates().entrySet()) {
            String attributeName = attributeTemplate.getKey();
            String attributeJson = "null";
            // TODO Find a better way of handling this! - Required because jackson will call the getNullValue() method
            // which doesn't have access to deserialisation context and therefore can't access the template
            if(attributeTemplate.getValue() instanceof SequenceTemplate){
                attributeJson = "\"\"";
            }
            ////////////////////////////////////////////
            if (root.has(attributeName)) {
                attributeJson = root.get(attributeName).toString();
            }
            Attribute attribute = (Attribute) mapper.reader(attributeTemplate.getValue().getAttributeType()).withAttribute("template", attributeTemplate.getValue()).readValue(attributeJson);
            if (attribute != null) {
                attribute.setTemplate(attributeTemplate.getValue());
            }
            instance.setAttribute(attributeName, attribute);
        }
        return instance;
    }

}
