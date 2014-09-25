package uk.co.revsys.objectology.action.model;

public class UpdateSelectAction extends AbstractAction {

    private String select;
    private String existingValue;
    private String value;

    public UpdateSelectAction() {
    }

    public UpdateSelectAction(String select) {
        this.select = select;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getExistingValue() {
        return existingValue;
    }

    public void setExistingValue(String existingValue) {
        this.existingValue = existingValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getNature() {
        return "updateSelect";
    }

}
