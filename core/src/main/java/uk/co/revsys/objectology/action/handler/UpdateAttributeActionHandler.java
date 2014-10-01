package uk.co.revsys.objectology.action.handler;

import uk.co.revsys.objectology.action.handler.exception.ActionInvocationException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.model.UpdateAttributeAction;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.exception.UnexpectedAttributeException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.AtomicAttribute;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.service.ServiceFactory;

public class UpdateAttributeActionHandler extends AbstractActionHandler<UpdateAttributeAction> {

    private final JsonInstanceMapper instanceMapper;

    public UpdateAttributeActionHandler(JsonInstanceMapper instanceMapper) {
        this.instanceMapper = instanceMapper;
    }

    @Override
    public OlogyInstance doInvoke(OlogyInstance instance, UpdateAttributeAction action, ActionRequest request) throws ActionInvocationException {
        try {
            String attributeName = action.getAttribute();
            String value = action.getValue();
            if (value == null) {
                value = getRequiredParameter(request, attributeName);
            }
            AttributeTemplate attributeTemplate = instance.getTemplate().getAttributeTemplate(attributeName);
            Class attributeType = attributeTemplate.getAttributeType();
            if (AtomicAttribute.class.isAssignableFrom(attributeType)) {
                value = "\"" + value + "\"";
            }
            Map<String, Object> deserialisationParameters = new HashMap<String, Object>();
            deserialisationParameters.put("template", attributeTemplate);
            Attribute attribute = (Attribute) instanceMapper.deserialise(value, attributeType, deserialisationParameters);
            instance.setAttribute(attributeName, attribute);
            return ServiceFactory.getOlogyInstanceService().update(instance);
        } catch (DeserialiserException ex) {
            throw new ActionInvocationException(ex);
        } catch (DaoException ex) {
            throw new ActionInvocationException(ex);
        } catch (UnexpectedAttributeException ex) {
            throw new ActionInvocationException(ex);
        } catch (ValidationException ex) {
            throw new ActionInvocationException(ex);
        }
    }

}
