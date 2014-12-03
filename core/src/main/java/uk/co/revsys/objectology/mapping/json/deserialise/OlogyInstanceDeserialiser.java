package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.Map.Entry;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.exception.RequiredAttributeException;
import uk.co.revsys.objectology.exception.UnexpectedAttributeException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.json.JSONNullType;
import uk.co.revsys.objectology.mapping.json.JsonObjectMapper;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.GeneratedAttribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.service.ServiceFactory;

public class OlogyInstanceDeserialiser extends JsonDeserializer<OlogyInstance> {

    @Override
    public OlogyInstance deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        try {
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
                        template = ServiceFactory.getOlogyTemplateService().findById(templateId);
                        if (template == null) {
                            throw new DeserialiserException("Template with id " + templateId + " not found");
                        }
                    } catch (DaoException ex) {
                        throw new DeserialiserException(ex);
                    }
                } else {
                    try {
                        String templateName = root.get("templateName").asText();
                        template = ServiceFactory.getOlogyTemplateService().findByName(templateName);
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
            for (Entry<String, AttributeTemplate> attributeTemplateEntry : template.getAttributeTemplates().entrySet()) {
                String attributeName = attributeTemplateEntry.getKey();
                AttributeTemplate attributeTemplate = attributeTemplateEntry.getValue();
                Attribute attribute;
                if (!attributeTemplate.isStatic()) {
                    if (GeneratedAttribute.class.isAssignableFrom(attributeTemplate.getAttributeType())) {
                        attribute = attributeTemplate.createDefaultInstance();
                    } else if (root.has(attributeName)) {
                        String attributeJson = root.get(attributeName).toString();
                        attribute = (Attribute) mapper.reader(attributeTemplate.getAttributeType()).withAttribute("instanceBundle", dc.getAttribute("instanceBundle")).withAttribute("template", attributeTemplate).readValue(attributeJson);
                    }else{
                        attribute = attributeTemplate.createDefaultInstance();
                    }
                    try {
                        try {
                            if (attribute != null) {
                                attribute.setTemplate(attributeTemplate);
                            }
                            instance.setAttribute(attributeName, attribute);
                        } catch (RequiredAttributeException ex) {
                            throw new RequiredAttributeException("Missing Attribute: " + attributeName);
                        }
                    } catch (UnexpectedAttributeException ex) {
                        throw new DeserialiserException(ex);
                    } catch (ValidationException ex) {
                        throw new DeserialiserException(ex);
                    }
                }
            }
            return instance;
        } catch (ValidationException ex) {
            throw new DeserialiserException(ex);
        }
    }

}
