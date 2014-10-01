package uk.co.revsys.objectology.action.model;

public class RemoveDictionaryEntryAction extends AbstractAction {

    private String dictionary;

    public RemoveDictionaryEntryAction() {
    }

    public RemoveDictionaryEntryAction(String dictionary) {
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
        return "removeDictionaryEntryAction";
    }

}
