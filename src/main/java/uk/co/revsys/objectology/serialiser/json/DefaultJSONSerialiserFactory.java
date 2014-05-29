package uk.co.revsys.objectology.serialiser.json;

import java.util.HashMap;
import java.util.LinkedList;
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
import uk.co.revsys.objectology.serialiser.Serialiser;
import uk.co.revsys.objectology.serialiser.SerialiserFactory;
import uk.co.revsys.objectology.view.IdentifiedObjectView;
import uk.co.revsys.objectology.view.SummaryObjectView;
import uk.co.revsys.objectology.view.RawView;

public class DefaultJSONSerialiserFactory extends SerialiserFactory {
    
	public DefaultJSONSerialiserFactory() {
		super(new HashMap<Class, Serialiser>() {
			{
				put(LinkedList.class, new JSONListSerialiser());
				put(Property.class, new JSONAtomicAttributeSerialiser());
				put(Time.class, new JSONAtomicAttributeSerialiser());
				put(Link.class, new JSONLinkSerialiser());
				put(Collection.class, new JSONCollectionSerialiser());
				put(Measurement.class, new JSONAtomicAttributeSerialiser());
				put(OlogyInstance.class, new JSONOlogyInstanceSerialiser());
				put(OlogyTemplate.class, new JSONOlogyTemplateSerialiser(new DefaultTemplateNatureMap()));
				put(CollectionTemplate.class, new JSONCollectionTemplateSerialiser(new DefaultTemplateNatureMap()));
				put(MeasurementTemplate.class, new JSONAttributeTemplateSerialiser(new DefaultTemplateNatureMap()));
				put(PropertyTemplate.class, new JSONAttributeTemplateSerialiser(new DefaultTemplateNatureMap()));
				put(TimeTemplate.class, new JSONAttributeTemplateSerialiser(new DefaultTemplateNatureMap()));
				put(LinkTemplate.class, new JSONAttributeTemplateSerialiser(new DefaultTemplateNatureMap()));
				put(IdentifiedObjectView.class, new DefaultJSONObjectSerialiser());
                                put(RawView.class, new JSONRawViewSerialiser());
			}
		}, new DefaultTemplateNatureMap());
	}

}
