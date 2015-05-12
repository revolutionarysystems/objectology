package uk.co.revsys.objectology.action.model;

import java.util.List;
import uk.co.revsys.objectology.security.SecurityConstraint;

public class CreateLinkedObjectAction extends AbstractAction {

    private String template;
    private String link;

    public CreateLinkedObjectAction() {
    }

    public CreateLinkedObjectAction(List<SecurityConstraint> securityConstraints) {
        super(securityConstraints);
    }

    public CreateLinkedObjectAction(String template) {
        this.template = template;
    }

    public CreateLinkedObjectAction(String template, List<SecurityConstraint> securityConstraints) {
        super(securityConstraints);
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String getNature() {
        return "createLinkedObject";
    }

}
