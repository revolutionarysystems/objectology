package uk.co.revsys.objectology.mapping.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.revsys.objectology.mapping.json.deserialise.GeneratedAttributeDeserialiser;
import uk.co.revsys.objectology.model.instance.LinkedObject;
import uk.co.revsys.objectology.model.instance.LinkedObjects;
import uk.co.revsys.objectology.model.template.OlogyTemplate;

public class JsonInstanceMapper extends JsonObjectMapper{

    public JsonInstanceMapper() {
        addMixInAnnotations(OlogyTemplate.class, OlogyTemplateMixin.class);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LinkedObject.class, new GeneratedAttributeDeserialiser(LinkedObject.class));
        module.addDeserializer(LinkedObjects.class, new GeneratedAttributeDeserialiser(LinkedObjects.class));
        registerModule(module);
    }

}
