package uk.co.revsys.objectology.serialiser.xml;

import java.util.HashMap;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.model.instance.Measurement;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.instance.Time;
import uk.co.revsys.objectology.serialiser.DefaultTemplateNatureMap;
import uk.co.revsys.objectology.serialiser.Serialiser;
import uk.co.revsys.objectology.serialiser.SerialiserFactory;

public class DefaultXMLSerialiserFactory extends SerialiserFactory {

    public DefaultXMLSerialiserFactory() {
        super(new HashMap<Class, Serialiser>() {
            {
                put(OlogyInstance.class, new XMLOlogyInstanceSerialiser());
                put(Property.class, new XMLAtomicAttributeSerialiser());
                put(Measurement.class, new XMLAtomicAttributeSerialiser());
                put(Time.class, new XMLAtomicAttributeSerialiser());
                put(Link.class, new XMLLinkSerialiser());
                put(Collection.class, new XMLCollectionSerialiser(new DefaultTemplateNatureMap()));
            }
        }, new DefaultTemplateNatureMap());
    }

}
