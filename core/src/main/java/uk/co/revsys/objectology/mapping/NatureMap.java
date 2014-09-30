package uk.co.revsys.objectology.mapping;

import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import uk.co.revsys.objectology.action.model.AddToCollectionAction;
import uk.co.revsys.objectology.action.model.CompoundAction;
import uk.co.revsys.objectology.action.model.RemoveFromCollectionAction;
import uk.co.revsys.objectology.action.model.UpdateAttributeAction;
import uk.co.revsys.objectology.action.model.UpdateSelectAction;
import uk.co.revsys.objectology.model.ObjectWithNature;
import uk.co.revsys.objectology.model.template.BooleanTemplate;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.LinkedObjectTemplate;
import uk.co.revsys.objectology.model.template.LinkedObjectsTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.SelectTemplate;
import uk.co.revsys.objectology.model.template.SequenceTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.security.IsAdministratorConstraint;
import uk.co.revsys.objectology.security.PermissionConstraint;
import uk.co.revsys.objectology.security.RoleConstraint;

public class NatureMap {

    private static final DualHashBidiMap<String, Class<? extends ObjectWithNature>> natureMap = new DualHashBidiMap<String, Class<? extends ObjectWithNature>>();
    
    static {
        // Attributes
        NatureMap.setNature("property", PropertyTemplate.class);
		NatureMap.setNature("measurement", MeasurementTemplate.class);
		NatureMap.setNature("time", TimeTemplate.class);
		NatureMap.setNature("link", LinkTemplate.class);
		NatureMap.setNature("collection", CollectionTemplate.class);
		NatureMap.setNature("object", OlogyTemplate.class);
        NatureMap.setNature("sequence", SequenceTemplate.class);
        NatureMap.setNature("linkedObject", LinkedObjectTemplate.class);
        NatureMap.setNature("linkedObjects", LinkedObjectsTemplate.class);
        NatureMap.setNature("select", SelectTemplate.class);
        NatureMap.setNature("boolean", BooleanTemplate.class);
        // Security
        NatureMap.setNature("hasRole", RoleConstraint.class);
        NatureMap.setNature("hasPermission", PermissionConstraint.class);
        NatureMap.setNature("isAdministrator", IsAdministratorConstraint.class);
        // Actions
        NatureMap.setNature("updateAttribute", UpdateAttributeAction.class);
        NatureMap.setNature("addToCollection", AddToCollectionAction.class);
        NatureMap.setNature("updateSelect", UpdateSelectAction.class);
        NatureMap.setNature("removeFromCollection", RemoveFromCollectionAction.class);
        NatureMap.setNature("compoundAction", CompoundAction.class);
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
