package uk.co.revsys.objectology.serialiser.json;

import org.apache.commons.collections4.BidiMap;
import org.json.JSONObject;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class JSONCollectionTemplateSerialiser extends JSONAttributeTemplateSerialiser<CollectionTemplate>{

    public JSONCollectionTemplateSerialiser(BidiMap<String, Class<? extends AttributeTemplate>> templateNatureMap) {
        super(templateNatureMap);
    }

	@Override
	public JSONObject doSerialiseJSON(JSONObject json, ObjectMapper objectMapper, CollectionTemplate object, Object args) throws SerialiserException {
		AttributeTemplate memberTemplate = object.getMemberTemplate();
		JSONSerialiser memberTemplateSerialiser = (JSONSerialiser) objectMapper.getSerialiser(memberTemplate.getClass());
		json.put("memberTemplate", memberTemplateSerialiser.serialiseJSON(objectMapper, memberTemplate, args));
		return json;
	}

}
