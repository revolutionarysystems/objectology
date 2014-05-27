package uk.co.revsys.objectology.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.AttributeTemplate;

public class OlogyInstanceValidator<I extends OlogyInstance> implements OlogyObjectValidator<I>{
	
	@Override
	public I validate(I instance) {
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

}
