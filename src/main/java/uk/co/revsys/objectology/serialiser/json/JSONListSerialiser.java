package uk.co.revsys.objectology.serialiser.json;

import java.util.List;
import org.json.JSONArray;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.Serialiser;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class JSONListSerialiser extends JSONSerialiser<List>{

	@Override
	public Object serialiseJSON(ObjectMapper objectMapper, List object, Object... args) throws SerialiserException {
		JSONArray jsonArray = new JSONArray();
		for(Object item: object){
			JSONSerialiser serialiser = (JSONSerialiser) objectMapper.getSerialiser(item.getClass());
			jsonArray.put(serialiser.serialiseJSON(objectMapper, item, args));
		}
		return jsonArray;
	}

}
