package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.view.RawView;

public class JSONRawViewDeserialiser extends JSONObjectDeserialiser<RawView>{

    @Override
    public RawView deserialiseJSON(ObjectMapper objectMapper, JSONObject source, Object... args) throws DeserialiserException {
        RawView view = new RawView();
        view.setRawData(source.toString());
        return view;
    }

}
