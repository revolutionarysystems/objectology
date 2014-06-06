package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;
import uk.co.revsys.objectology.view.RawView;

public class JSONRawViewSerialiser extends JSONSerialiser<RawView>{

    @Override
    public Object serialiseJSON(ObjectMapper objectMapper, RawView object, Object... args) throws SerialiserException {
        return new JSONObject(object.getRawData());
    }

}
