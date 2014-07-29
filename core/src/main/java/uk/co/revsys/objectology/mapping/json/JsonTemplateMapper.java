package uk.co.revsys.objectology.mapping.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.revsys.objectology.action.model.Action;
import uk.co.revsys.objectology.mapping.json.deserialise.ObjectWithNatureDeserialiser;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.security.SecurityConstraint;

public class JsonTemplateMapper extends JsonObjectMapper{

    public JsonTemplateMapper() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(AttributeTemplate.class, new ObjectWithNatureDeserialiser<AttributeTemplate>());
        module.addDeserializer(SecurityConstraint.class, new ObjectWithNatureDeserialiser<SecurityConstraint>());
        module.addDeserializer(Action.class, new ObjectWithNatureDeserialiser<Action>());
        registerModule(module);
    }

}
