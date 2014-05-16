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
			String templateId = json.getString("template");
			try {
				template = templateService.findById(templateId);
			} catch (DaoException ex) {
				throw new DeserialiserException(ex);
			}
		} else {
			template = (OlogyTemplate) args[0];
		}
		OlogyInstance instance = new OlogyInstance();
		instance.setTemplate(template);
		instance.setId(json.getString("id"));
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
