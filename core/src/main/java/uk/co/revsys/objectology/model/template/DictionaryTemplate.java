package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Dictionary;

public class DictionaryTemplate extends AbstractAttributeTemplate<Dictionary>{

    private AttributeTemplate memberTemplate;

    public DictionaryTemplate() {
    }

    public DictionaryTemplate(AttributeTemplate memberTemplate) {
        this.memberTemplate = memberTemplate;
    }

    public AttributeTemplate getMemberTemplate() {
        return memberTemplate;
    }

    public void setMemberTemplate(AttributeTemplate memberTemplate) {
        this.memberTemplate = memberTemplate;
    }
    
    @Override
    public Class<? extends Dictionary> getAttributeType() {
        return Dictionary.class;
    }

    @Override
    public Dictionary createDefaultInstance() {
        return new Dictionary();
    }

    @Override
    public String getNature() {
        return "dictionary";
    }

}
