package uk.co.revsys.objectology.action.model;

public class UpdateDictionaryAction extends AbstractAction{

    private String dictionary;

    public UpdateDictionaryAction() {
    }

    public UpdateDictionaryAction(String dictionary) {
        this.dictionary = dictionary;
    }

    public String getDictionary() {
        return dictionary;
    }

    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }
    
    @Override
    public String getNature() {
        return "updateDictionary";
    }

}
