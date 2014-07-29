package uk.co.revsys.objectology.mapping;

import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import uk.co.revsys.objectology.action.model.AddToCollectionAction;
import uk.co.revsys.objectology.action.model.UpdateAttributeAction;
import uk.co.revsys.objectology.model.ObjectWithNature;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.LinkedObjectTemplate;
import uk.co.revsys.objectology.model.template.LinkedObjectsTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.SequenceTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.security.RoleConstraint;

public class NatureMap {

    private static final DualHashBidiMap<String, Class<? extends ObjectWithNature>> natureMap = new DualHashBidiMap<String, Class<? extends ObjectWithNature>>();
    
    static {
        NatureMap.setNature("property", PropertyTemplate.class);
		NatureMap.setNature("measurement", MeasurementTemplate.class);
		NatureMap.setNature("time", TimeTemplate.class);
		NatureMap.setNature("link", LinkTemplate.class);
		NatureMap.setNature("collection", CollectionTemplate.class);
		NatureMap.setNature("object", OlogyTemplate.class);
        NatureMap.setNature("sequence", SequenceTemplate.class);
        NatureMap.setNature("roleConstraint", RoleConstraint.class);
        NatureMap.setNature("updateAttributeAction", UpdateAttributeAction.class);
        NatureMap.setNature("addToCollectionAction", AddToCollectionAction.class);
        NatureMap.setNature("linkedObject", LinkedObjectTemplate.class);
        NatureMap.setNature("linkedObjects", LinkedObjectsTemplate.class);
    }
    
    public static String getTemplateNature(Class<? extends ObjectWithNature> templateType){
        return natureMap.getKey(templateType);
    }
    
    public static Class<? extends ObjectWithNature> getTemplateType(String nature){
        return natureMap.get(nature);
    }
    
    public static void setNature(String nature, Class<? extends ObjectWithNature> type){
        natureMap.put(nature, type);
    }
    
}
