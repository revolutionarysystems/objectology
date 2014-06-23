package uk.co.revsys.objectology.mapping.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.revsys.objectology.mapping.json.deserialise.AttributeTemplateDeserialiser;
import uk.co.revsys.objectology.model.template.AttributeTemplate;

public class JsonTemplateMapper extends JsonObjectMapper{

    public JsonTemplateMapper() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(AttributeTemplate.class, new AttributeTemplateDeserialiser());
        registerModule(module);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}
