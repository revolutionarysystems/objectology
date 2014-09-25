package uk.co.revsys.objectology.action.model;

public class UpdateAttributeAction extends AbstractAction {

    private String attribute;
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getNature() {
        return "updateAttribute";
    }

}
