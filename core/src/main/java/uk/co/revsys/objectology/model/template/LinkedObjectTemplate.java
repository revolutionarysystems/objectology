package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.LinkedObject;

public class LinkedObjectTemplate extends AbstractAttributeTemplate<LinkedObject>{

    private String type;
    private String link;

    public LinkedObjectTemplate() {
    }

    public LinkedObjectTemplate(String type, String link) {
        this.type = type;
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    @Override
    public Class<? extends LinkedObject> getAttributeType() {
        return LinkedObject.class;
    }

    @Override
    public String getNature() {
        return "linkedObject";
    }

}
