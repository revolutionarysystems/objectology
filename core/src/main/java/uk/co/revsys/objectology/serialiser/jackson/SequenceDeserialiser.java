package uk.co.revsys.objectology.serialiser.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import java.io.IOException;
import uk.co.revsys.objectology.dao.SequenceGenerator;
import uk.co.revsys.objectology.model.instance.Sequence;
import uk.co.revsys.objectology.model.template.SequenceTemplate;

public class SequenceDeserialiser extends JsonDeserializer<Sequence> implements ContextualDeserializer{

    private final SequenceGenerator sequenceGenerator;

    public SequenceDeserialiser(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext dc, BeanProperty bp) throws JsonMappingException {
        ContextualObjectMapper mapper = (ContextualObjectMapper) dc.getParser().getCodec();
        SequenceTemplate template = (SequenceTemplate) mapper.getThreadContext().get("template");
        return new SequenceDeserialiserInstance(sequenceGenerator, template);
    }

    @Override
    public Sequence deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
