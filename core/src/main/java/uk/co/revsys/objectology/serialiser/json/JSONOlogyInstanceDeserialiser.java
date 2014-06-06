package uk.co.revsys.objectology.serialiser.json;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.service.OlogyTemplateService;

public class JSONOlogyInstanceDeserialiser extends JSONObjectDeserialiser<OlogyInstance> {

    private final OlogyTemplateService<OlogyTemplate> templateService;

    public JSONOlogyInstanceDeserialiser(OlogyTemplateService<OlogyTemplate> templateService) {
        this.templateService = templateService;
    }

    @Override
    public OlogyInstance deserialiseJSON(ObjectMapper objectMapper, JSONObject json, Object... args) throws DeserialiserException {
        OlogyTemplate template;
        if (args == null || args.length == 0) {
            try {
                System.out.println(json.toString());
                String templateId = json.optString("template");
                if (templateId != null && !templateId.isEmpty()) {
                    template = templateService.findById(templateId);
                } else {
                    templateId = json.optString("templateId");
                    if (templateId != null && !templateId.isEmpty()) {
                        template = templateService.findById(templateId);
                    } else {
                        String templateName = json.getString("templateName");
                        System.out.println("templateName = " + templateName);
                        template = templateService.findByName(templateName);
                    }
                }
            } catch (DaoException ex) {
                throw new DeserialiserException(ex);
            }
        } else {
            template = (OlogyTemplate) args[0];
        }
        System.out.println("template = " + template);
        OlogyInstance instance = new OlogyInstance();
        instance.setTemplate(template);
        String id = json.optString("id");
        if (id != null && !id.isEmpty()) {
            instance.setId(id);
        }
        json.remove("template");
        for (Object key : json.keySet()) {
            String attributeName = (String) key;
            AttributeTemplate attributeTemplate = template.getAttributeTemplate(attributeName);
            if (attributeTemplate != null) {
                JSONDeserialiser instanceSerialiser = (JSONDeserialiser) objectMapper.getDeserialiser(attributeTemplate.getAttributeType());
                Object attributeJson = json.get(attributeName);
                Attribute attribute;
                attribute = (Attribute) instanceSerialiser.deserialiseJSON(objectMapper, attributeJson, attributeTemplate);
                instance.setAttribute(attributeName, attribute);
            }
        }
        return instance;
    }

}
