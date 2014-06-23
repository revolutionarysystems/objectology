package uk.co.revsys.objectology.mapping.json;

import com.fasterxml.jackson.annotation.JsonValue;

public abstract class OlogyTemplateMixin {

    @JsonValue
    public abstract String getId();
    
}
