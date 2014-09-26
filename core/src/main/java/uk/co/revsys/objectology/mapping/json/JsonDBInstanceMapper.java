package uk.co.revsys.objectology.mapping.json;

import uk.co.revsys.objectology.model.instance.LinkedObject;
import uk.co.revsys.objectology.model.instance.LinkedObjects;

public class JsonDBInstanceMapper extends JsonInstanceMapper{

    public JsonDBInstanceMapper() {
        super();
        addMixInAnnotations(LinkedObject.class, GeneratedAttributeMixin.class);
        addMixInAnnotations(LinkedObjects.class, GeneratedAttributeMixin.class);
    }

}
