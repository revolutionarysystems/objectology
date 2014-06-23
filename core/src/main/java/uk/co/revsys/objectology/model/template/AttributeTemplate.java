package uk.co.revsys.objectology.model.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.mapping.json.serialise.NatureSerialiser;

public interface AttributeTemplate<A extends Attribute> {
	
	public A getValue();
	
	public void setValue(A value);
	
    @JsonProperty("nature")
    @JsonSerialize(using = NatureSerialiser.class)
	public Class<? extends A> getAttributeType();
	
}
