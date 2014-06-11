package uk.co.revsys.objectology.serialiser.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.co.revsys.objectology.dao.SequenceException;
import uk.co.revsys.objectology.dao.SequenceGenerator;
import uk.co.revsys.objectology.model.instance.Sequence;
import uk.co.revsys.objectology.model.template.SequenceTemplate;

public class SequenceDeserialiserInstance extends JsonDeserializer<Sequence>{

    private final SequenceGenerator sequenceGenerator;
    private final SequenceTemplate template;

    public SequenceDeserialiserInstance(SequenceGenerator sequenceGenerator, SequenceTemplate template) {
        this.sequenceGenerator = sequenceGenerator;
        this.template = template;
    }

    @Override
    public Sequence deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        String value = jp.readValueAs(String.class);
        return new Sequence(value);
    }

    @Override
    public Sequence getNullValue() {
        try {
            String value = sequenceGenerator.getNextSequence(template.getName(), template.getLength());
            return new Sequence(value);
        } catch (SequenceException ex) {
            throw new RuntimeException(ex);
        }
    }

    
    
    
}
