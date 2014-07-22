package uk.co.revsys.objectology.mapping.json.serialise;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.Link;

public class LinkSerialiser extends JsonSerializer<Link> {

    @Override
    public void serialize(Link link, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        int depth = (Integer) sp.getAttribute("depth");
        if (depth > 1) {
            try {
                JsonInstanceMapper mapper = (JsonInstanceMapper) jg.getCodec();
                String json = mapper.serialise(link.getAssociatedObject(), depth-1);
                jg.writeRaw(":" + json);
            } catch (DaoException ex) {
                throw new DeserialiserException(ex);
            }
        } else {
            jg.writeString(link.getReference());
        }
    }

}
