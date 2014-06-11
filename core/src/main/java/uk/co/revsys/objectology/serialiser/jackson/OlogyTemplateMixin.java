package uk.co.revsys.objectology.serialiser.jackson;

import com.fasterxml.jackson.annotation.JsonValue;

public abstract class OlogyTemplateMixin {

    @JsonValue
    public abstract String getId();
    
}
