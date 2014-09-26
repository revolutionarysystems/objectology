package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.LinkedObject;
import uk.co.revsys.objectology.model.instance.LinkedObjects;

public class LinkedObjectsTemplate extends AbstractAttributeTemplate<LinkedObjects>{

    private String type;
    private String link;

    public LinkedObjectsTemplate() {
    }

    public LinkedObjectsTemplate(String type, String link) {
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
    public LinkedObjects newInstance() {
        return new LinkedObjects();
    }
    
    @Override
    public Class<? extends LinkedObjects> getAttributeType() {
        return LinkedObjects.class;
    }

    @Override
    public String getNature() {
        return "linkedObjects";
    }

}
