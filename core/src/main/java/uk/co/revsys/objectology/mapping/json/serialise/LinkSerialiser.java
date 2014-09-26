package uk.co.revsys.objectology.mapping.json.serialise;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.PersistedObject;
import uk.co.revsys.objectology.model.instance.AbstractLink;

public class LinkSerialiser extends JsonSerializer<AbstractLink> {

    @Override
    public void serialize(AbstractLink link, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        Integer depth = (Integer) sp.getAttribute("depth");
        if(depth == null){
            depth = 1;
        }
        if (depth > 1) {
            try {
                JsonInstanceMapper mapper = (JsonInstanceMapper) jg.getCodec();
                PersistedObject associatedObject = link.getAssociatedObject();
                if (associatedObject != null) {
                    sp.setAttribute("depth", depth - 1);
                    sp.defaultSerializeValue(associatedObject, jg);
                    sp.setAttribute("depth", depth);
                }else{
                    jg.writeNull();
                }
            } catch (DaoException ex) {
                throw new DeserialiserException(ex);
            }
        } else {
            jg.writeString(link.getReference());
        }
    }

}
