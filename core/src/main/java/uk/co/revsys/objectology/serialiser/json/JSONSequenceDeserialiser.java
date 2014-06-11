package uk.co.revsys.objectology.serialiser.json;

import uk.co.revsys.objectology.dao.SequenceException;
import uk.co.revsys.objectology.dao.SequenceGenerator;
import uk.co.revsys.objectology.model.instance.Sequence;
import uk.co.revsys.objectology.model.template.SequenceTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class JSONSequenceDeserialiser extends JSONStringDeserialiser<Sequence>{

    private final SequenceGenerator sequenceGenerator;

    public JSONSequenceDeserialiser(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public Sequence deserialiseJSON(ObjectMapper objectMapper, String source, Object... args) throws DeserialiserException {
        if(source==null || source.isEmpty()){
            SequenceTemplate template = (SequenceTemplate) args[0];
            try {
                source = sequenceGenerator.getNextSequence(template.getName(), template.getLength());
            } catch (SequenceException ex) {
                throw new DeserialiserException(ex);
            }
        }
        return new Sequence(source);
    }
}
