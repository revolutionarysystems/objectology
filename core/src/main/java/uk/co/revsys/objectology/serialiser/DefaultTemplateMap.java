package uk.co.revsys.objectology.serialiser;

import java.util.HashMap;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.model.instance.Measurement;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.instance.Time;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;

public class DefaultTemplateMap extends HashMap<Class<? extends AttributeTemplate>, Class<? extends Attribute>>{

	public DefaultTemplateMap() {
		put(PropertyTemplate.class, Property.class);
		put(TimeTemplate.class, Time.class);
		put(MeasurementTemplate.class, Measurement.class);
		put(LinkTemplate.class, Link.class);
		put(CollectionTemplate.class, Collection.class);
		put(OlogyTemplate.class, OlogyInstance.class);
	}

}
