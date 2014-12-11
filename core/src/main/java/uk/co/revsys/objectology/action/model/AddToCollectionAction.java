package uk.co.revsys.objectology.action.model;

public class AddToCollectionAction extends AttributeAction {

    private String collection;
    private String item;

    public AddToCollectionAction() {
    }

    public AddToCollectionAction(String collection, String item) {
        this.collection = collection;
        this.item = item;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public String getNature() {
        return "addToCollection";
    }

}
