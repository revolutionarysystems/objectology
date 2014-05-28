package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONArray;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class JSONCollectionSerialiser extends JSONAttributeSerialiser<Collection> {

    @Override
    public Object serialiseJSON(ObjectMapper objectMapper, Collection object, Object... args) throws SerialiserException {
        JSONArray json = new JSONArray();
        if (object != null) {
            for (Attribute member : object.getMembers()) {
                JSONAttributeSerialiser memberSerialiser = (JSONAttributeSerialiser) objectMapper.getSerialiser(member.getClass());
                json.put(memberSerialiser.serialiseJSON(objectMapper, member));
            }
        }
        return json;
    }

}
