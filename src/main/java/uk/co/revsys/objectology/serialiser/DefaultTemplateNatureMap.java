package uk.co.revsys.objectology.serialiser;

import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;

public class DefaultTemplateNatureMap extends DualHashBidiMap<String, Class<? extends AttributeTemplate>>{

	public DefaultTemplateNatureMap() {
		put("property", PropertyTemplate.class);
		put("measurement", MeasurementTemplate.class);
		put("time", TimeTemplate.class);
		put("link", LinkTemplate.class);
		put("collection", CollectionTemplate.class);
		put("object", OlogyTemplate.class);
	}
	
}
