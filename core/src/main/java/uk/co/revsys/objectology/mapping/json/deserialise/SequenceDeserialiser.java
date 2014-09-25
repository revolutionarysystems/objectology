package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import uk.co.revsys.objectology.dao.SequenceException;
import uk.co.revsys.objectology.dao.SequenceGenerator;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.Sequence;
import uk.co.revsys.objectology.model.template.SequenceTemplate;

public class SequenceDeserialiser extends JsonDeserializer<Sequence>{

    private final SequenceGenerator sequenceGenerator;

    public SequenceDeserialiser(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public Sequence deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        String value = jp.readValueAs(String.class);
        SequenceTemplate template = (SequenceTemplate) dc.getAttribute("template");
        if(value.isEmpty()){
            try {
                value = sequenceGenerator.getNextSequence(template.getName(), template.getLength());
            } catch (SequenceException ex) {
                throw new DeserialiserException(ex);
            }
        }
        try {
            return new Sequence(value);
        } catch (ValidationException ex) {
            throw new DeserialiserException(ex);
        }
    }

}
