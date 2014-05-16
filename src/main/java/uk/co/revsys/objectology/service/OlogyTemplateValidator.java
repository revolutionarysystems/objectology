package uk.co.revsys.objectology.service;

import java.util.UUID;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;

public class OlogyTemplateValidator<T extends OlogyTemplate> implements OlogyObjectValidator<T>{

	@Override
	public T validate(T template) {
		template = identify(template);
		return template;
	}
	
	public T identify(T template){
		if(template.getId()==null){
			template.setId(UUID.randomUUID().toString());
		}
		for(AttributeTemplate attributeTemplate: template.getAttributeTemplates().values()){
			if(attributeTemplate instanceof OlogyTemplate){
				identify((T) attributeTemplate);
			}
		}
		return template;
	}

}
