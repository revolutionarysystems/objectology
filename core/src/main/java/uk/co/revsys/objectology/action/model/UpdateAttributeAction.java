package uk.co.revsys.objectology.action.model;

public class UpdateAttributeAction extends AbstractAction {

    private String attribute;

    public UpdateAttributeAction() {
    }

    public UpdateAttributeAction(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getNature() {
        return "updateAttribute";
    }

}
