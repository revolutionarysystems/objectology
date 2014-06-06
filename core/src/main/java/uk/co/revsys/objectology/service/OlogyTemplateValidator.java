package uk.co.revsys.objectology.service;

import uk.co.revsys.objectology.model.template.OlogyTemplate;

public class OlogyTemplateValidator<T extends OlogyTemplate> implements OlogyObjectValidator<T>{

	@Override
	public T validate(T template) {
		return template;
	}

}
