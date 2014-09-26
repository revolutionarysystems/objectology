package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Collection;

public class CollectionTemplate extends AbstractAttributeTemplate<Collection>{

	private AttributeTemplate memberTemplate;

	public CollectionTemplate() {
	}

	public CollectionTemplate(AttributeTemplate memberTemplate) {
		this.memberTemplate = memberTemplate;
	}

	public AttributeTemplate getMemberTemplate() {
		return memberTemplate;
	}

	public void setMemberTemplate(AttributeTemplate memberTemplate) {
		this.memberTemplate = memberTemplate;
	}

    @Override
    public Collection newInstance() {
        return new Collection();
    }

	@Override
	public Class<? extends Collection> getAttributeType() {
		return Collection.class;
	}

    @Override
    public String getNature() {
        return "collection";
    }
	
}
