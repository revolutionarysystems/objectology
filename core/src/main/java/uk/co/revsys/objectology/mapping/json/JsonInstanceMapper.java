package uk.co.revsys.objectology.mapping.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.HashMap;
import java.util.Map;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.json.deserialise.GeneratedAttributeDeserialiser;
import uk.co.revsys.objectology.model.instance.AtomicAttribute;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.LinkedObject;
import uk.co.revsys.objectology.model.instance.LinkedObjects;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;

public class JsonInstanceMapper extends JsonObjectMapper {

    public JsonInstanceMapper() {
        addMixInAnnotations(OlogyTemplate.class, OlogyTemplateMixin.class);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LinkedObject.class, new GeneratedAttributeDeserialiser(LinkedObject.class));
        module.addDeserializer(LinkedObjects.class, new GeneratedAttributeDeserialiser(LinkedObjects.class));
        registerModule(module);
    }

    public Attribute deserialise(String source, AttributeTemplate template) throws DeserialiserException {
        Class attributeType = template.getAttributeType();
        if (AtomicAttribute.class.isAssignableFrom(attributeType)) {
            source = "\"" + source + "\"";
        }
        Map<String, Object> deserialisationParameters = new HashMap<String, Object>();
        deserialisationParameters.put("template", template);
        Attribute attribute = (Attribute) deserialise(source, attributeType, deserialisationParameters);
        return attribute;
    }

}
