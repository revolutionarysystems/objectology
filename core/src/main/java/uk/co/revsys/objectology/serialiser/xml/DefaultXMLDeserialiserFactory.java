package uk.co.revsys.objectology.serialiser.xml;

import java.util.HashMap;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.model.instance.Measurement;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.instance.Time;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.serialiser.DefaultTemplateNatureMap;
import uk.co.revsys.objectology.serialiser.Deserialiser;
import uk.co.revsys.objectology.serialiser.DeserialiserFactory;
import uk.co.revsys.objectology.service.OlogyTemplateService;

public class DefaultXMLDeserialiserFactory extends DeserialiserFactory {

	public DefaultXMLDeserialiserFactory(final OlogyTemplateService templateService) {
		super(new HashMap<Class, Deserialiser>() {
			{
				put(OlogyTemplate.class, new XMLOlogyTemplateDeserialiser());
				put(PropertyTemplate.class, new XMLAtomicAttributeTemplateDeserialiser(PropertyTemplate.class));
				put(LinkTemplate.class, new XMLLinkTemplateDeserialiser());
				put(TimeTemplate.class, new XMLAtomicAttributeTemplateDeserialiser(TimeTemplate.class));
				put(CollectionTemplate.class, new XMLCollectionTemplateDeserialiser());
				put(MeasurementTemplate.class, new XMLAtomicAttributeTemplateDeserialiser(MeasurementTemplate.class));
				put(OlogyInstance.class, new XMLOlogyInstanceDeserialiser(templateService));
				put(Property.class, new XMLAtomicAttributeDeserialiser(Property.class));
				put(Link.class, new XMLLinkDeserialiser());
				put(Time.class, new XMLAtomicAttributeDeserialiser(Time.class));
				put(Collection.class, new XMLCollectionDeserialiser());
				put(Measurement.class, new XMLAtomicAttributeDeserialiser(Measurement.class));
			}
		}, new DefaultTemplateNatureMap());
	}

}
