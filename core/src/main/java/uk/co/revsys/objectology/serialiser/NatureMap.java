package uk.co.revsys.objectology.serialiser;

import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.model.instance.Measurement;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.instance.Sequence;
import uk.co.revsys.objectology.model.instance.Time;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.SequenceTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;

public class NatureMap {

    private static final DualHashBidiMap<String, Class<? extends AttributeTemplate>> templateNatureMap = new DualHashBidiMap<String, Class<? extends AttributeTemplate>>();
    private static final DualHashBidiMap<String, Class<? extends Attribute>> instanceNatureMap = new DualHashBidiMap<String, Class<? extends Attribute>>();
    
    static {
        NatureMap.setNature("property", PropertyTemplate.class, Property.class);
		NatureMap.setNature("measurement", MeasurementTemplate.class, Measurement.class);
		NatureMap.setNature("time", TimeTemplate.class, Time.class);
		NatureMap.setNature("link", LinkTemplate.class, Link.class);
		NatureMap.setNature("collection", CollectionTemplate.class, Collection.class);
		NatureMap.setNature("object", OlogyTemplate.class, OlogyInstance.class);
        NatureMap.setNature("sequence", SequenceTemplate.class, Sequence.class);
    }
    
    public static String getTemplateNature(Class<? extends AttributeTemplate> templateType){
        return templateNatureMap.getKey(templateType);
    }
    
    public static String getInstanceNature(Class<? extends Attribute> instanceType){
        return instanceNatureMap.getKey(instanceType);
    }
    
    public static Class<? extends AttributeTemplate> getTemplateType(String nature){
        return templateNatureMap.get(nature);
    }
    
    public static Class<? extends Attribute> getInstanceType(String nature){
        return instanceNatureMap.get(nature);
    }
    
    public static void setNature(String nature, Class<? extends AttributeTemplate> templateType, Class<? extends Attribute> instanceType){
        templateNatureMap.put(nature, templateType);
        instanceNatureMap.put(nature, instanceType);
    }
    
}
