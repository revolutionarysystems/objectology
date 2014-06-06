package uk.co.revsys.objectology.serialiser.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.revsys.objectology.model.template.AttributeTemplate;

public abstract class JacksonAttributeTemplateMixin<A extends AttributeTemplate> {

	@JsonIgnore
	public abstract A getValue();
	
	@JsonIgnore
	public abstract Class<? extends A> getAttributeType();

}
