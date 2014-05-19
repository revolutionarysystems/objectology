package uk.co.revsys.objectology.serialiser.json;

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

public class DefaultJSONDeserialiserFactory extends DeserialiserFactory {

	public DefaultJSONDeserialiserFactory(final OlogyTemplateService templateService) {
		super(new HashMap<Class, Deserialiser>() {
			{
				put(Property.class, new JSONAtomicAttributeDeserialiser(Property.class));
				put(Time.class, new JSONAtomicAttributeDeserialiser(Time.class));
				put(Link.class, new JSONLinkDeserialiser());
				put(Collection.class, new JSONCollectionDeserialiser());
				put(Measurement.class, new JSONAtomicAttributeDeserialiser(Measurement.class));
				put(OlogyInstance.class, new JSONOlogyInstanceDeserialiser(templateService));
				put(OlogyTemplate.class, new JSONOlogyTemplateDeserialiser());
				put(PropertyTemplate.class, new JSONPropertyTemplateDeserialiser());
				put(MeasurementTemplate.class, new JSONMeasurementTemplateDeserialiser());
				put(TimeTemplate.class, new JSONTimeTemplateDeserialiser());
				put(LinkTemplate.class, new JSONLinkTemplateDeserialiser());
				put(CollectionTemplate.class, new JSONCollectionTemplateDeserialiser());
			}
		}, new DefaultTemplateNatureMap());
	}

}
