package uk.co.revsys.objectology.serialiser.json;

import java.util.HashMap;
import java.util.LinkedList;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.model.instance.Measurement;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.instance.Time;
import uk.co.revsys.objectology.serialiser.DefaultTemplateNatureMap;
import uk.co.revsys.objectology.serialiser.Serialiser;
import uk.co.revsys.objectology.serialiser.SerialiserFactory;

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
			}
		}, new DefaultTemplateNatureMap());
	}

}
