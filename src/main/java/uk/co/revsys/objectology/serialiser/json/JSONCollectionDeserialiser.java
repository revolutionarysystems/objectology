package uk.co.revsys.objectology.serialiser.json;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class JSONCollectionDeserialiser extends JSONArrayDeserialiser<Collection>{

	@Override
	public Collection deserialiseJSON(ObjectMapper objectMapper, JSONArray jsonArray, Object... args) throws DeserialiserException {
		CollectionTemplate template = (CollectionTemplate) args[0];
		Collection collection = new Collection();
		List members = new LinkedList();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				Object memberJson = jsonArray.get(i);
				Attribute member;
				AttributeTemplate memberTemplate = template.getMemberTemplate();
				Class memberType = template.getMemberTemplate().getClass().newInstance().getAttributeType();
				JSONDeserialiser memberDeserialiser = (JSONDeserialiser) objectMapper.getDeserialiser(memberType);
				member = (Attribute) memberDeserialiser.deserialiseJSON(objectMapper, memberJson, memberTemplate);
				members.add(member);
			} catch (InstantiationException ex) {
				throw new DeserialiserException(ex);
			} catch (IllegalAccessException ex) {
				throw new DeserialiserException(ex);
			}
		}
		collection.setMembers(members);
		return collection;
	}

}
