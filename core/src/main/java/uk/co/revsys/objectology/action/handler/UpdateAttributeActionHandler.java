package uk.co.revsys.objectology.action.handler;

import java.util.HashMap;
import java.util.Map;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.model.UpdateAttributeAction;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.AtomicAttribute;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.service.OlogyObjectServiceFactory;

public class UpdateAttributeActionHandler extends AbstractActionHandler<UpdateAttributeAction>{

    private final JsonInstanceMapper instanceMapper;

    public UpdateAttributeActionHandler(JsonInstanceMapper instanceMapper) {
        this.instanceMapper = instanceMapper;
    }
    
    @Override
    public OlogyInstance invoke(OlogyInstance instance, UpdateAttributeAction action, ActionRequest request) throws ActionInvocationException {
        try {
            String attributeName = action.getAttribute();
            String value = getRequiredParameter(request, attributeName);
            AttributeTemplate attributeTemplate = instance.getTemplate().getAttributeTemplate(attributeName);
            Class attributeType = attributeTemplate.getAttributeType();
            if(AtomicAttribute.class.isAssignableFrom(attributeType)){
                value = "\"" + value + "\"";
            }
            Map<String, Object> deserialisationParameters = new HashMap<String, Object>();
            deserialisationParameters.put("template", attributeTemplate);
            Attribute attribute = (Attribute) instanceMapper.deserialise(value, attributeType, deserialisationParameters);
            instance.setAttribute(attributeName, attribute);
            return OlogyObjectServiceFactory.getOlogyInstanceService().update(instance);
        } catch (DeserialiserException ex) {
            throw new ActionInvocationException(ex);
        } catch (DaoException ex) {
            throw new ActionInvocationException(ex);
        }
    }

}
