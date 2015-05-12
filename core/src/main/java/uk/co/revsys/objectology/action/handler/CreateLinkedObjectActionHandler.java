package uk.co.revsys.objectology.action.handler;

import java.io.IOException;
import org.json.JSONObject;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.handler.exception.ActionInvocationException;
import uk.co.revsys.objectology.action.model.CreateLinkedObjectAction;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.exception.UnexpectedAttributeException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.service.ServiceFactory;

public class CreateLinkedObjectActionHandler extends AbstractActionHandler<CreateLinkedObjectAction> {

    private final JsonInstanceMapper instanceMapper;

    public CreateLinkedObjectActionHandler(JsonInstanceMapper instanceMapper) {
        this.instanceMapper = instanceMapper;
    }

    @Override
    public OlogyInstance doInvoke(OlogyInstance instance, CreateLinkedObjectAction action, ActionRequest request) throws ActionInvocationException {
        try {
            OlogyTemplate template = ServiceFactory.getOlogyTemplateService().findByName(action.getTemplate());
            JSONObject json = new JSONObject(getRequiredParameter(request, template.getType()));
            json.put("template", template.getId());
            System.out.println(json.toString());
            OlogyInstance linkedObject = instanceMapper.readValue(json.toString(), template.getAttributeType());
            String link = action.getLink();
            if(link == null){
                link = instance.getType();
            }
            System.out.println("link = " + link);
            linkedObject.setAttribute(instance.getType(), new Link(instance.getId()));
            linkedObject = ServiceFactory.getOlogyInstanceService().create(linkedObject);
            return instance;
        } catch (DaoException ex) {
            throw new ActionInvocationException(ex);
        } catch (IOException ex) {
            throw new ActionInvocationException(ex);
        } catch (UnexpectedAttributeException ex) {
            throw new ActionInvocationException(ex);
        } catch (ValidationException ex) {
            throw new ActionInvocationException(ex);
        }
    }

}
