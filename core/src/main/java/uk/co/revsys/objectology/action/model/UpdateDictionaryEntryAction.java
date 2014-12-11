package uk.co.revsys.objectology.action.model;

public class UpdateDictionaryEntryAction extends AttributeAction{

    private String dictionary;

    public UpdateDictionaryEntryAction() {
    }

    public UpdateDictionaryEntryAction(String dictionary) {
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
        return "updateDictionaryEntry";
    }

}
