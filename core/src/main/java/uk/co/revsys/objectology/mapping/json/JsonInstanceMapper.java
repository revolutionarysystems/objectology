package uk.co.revsys.objectology.mapping.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.revsys.objectology.dao.SequenceGenerator;
import uk.co.revsys.objectology.mapping.json.deserialise.SequenceDeserialiser;
import uk.co.revsys.objectology.model.instance.Sequence;
import uk.co.revsys.objectology.model.template.OlogyTemplate;

public class JsonInstanceMapper extends JsonObjectMapper{

    public JsonInstanceMapper(SequenceGenerator sequenceGenerator) {
        addMixInAnnotations(OlogyTemplate.class, OlogyTemplateMixin.class);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Sequence.class, new SequenceDeserialiser(sequenceGenerator));
        registerModule(module);
    }

}
