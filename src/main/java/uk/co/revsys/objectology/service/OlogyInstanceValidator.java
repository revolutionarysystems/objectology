package uk.co.revsys.objectology.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.AttributeTemplate;

public class OlogyInstanceValidator<I extends OlogyInstance> implements OlogyObjectValidator<I>{
	
	@Override
	public I validate(I instance) {
		instance = identify(instance);
		OlogyTemplate template = instance.getTemplate();
		List<String> expectedProperties = new ArrayList<String>();
		for(Entry<String, AttributeTemplate> property: template.getAttributeTemplates().entrySet()){
			expectedProperties.add(property.getKey());
		}
		Iterator<String> iterator = instance.getAttributes().keySet().iterator();
		while(iterator.hasNext()){
			if(!expectedProperties.contains(iterator.next())){
				iterator.remove();
			}
		}
		return instance;
	}
	
	public I identify(I instance){
		if(instance.getId()==null){
			instance.setId(UUID.randomUUID().toString());
		}
		for(Attribute attribute: instance.getAttributes().values()){
			if(attribute instanceof OlogyInstance){
				identify((I) attribute);
			}else if(attribute instanceof Collection){
				for(Attribute member: ((Collection)attribute).getMembers()){
					if(member instanceof OlogyInstance){
						identify((I) member);
					}
				}
			}
		}
		return instance;
	}

}
