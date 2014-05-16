package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Collection;

public class CollectionTemplate implements AttributeTemplate{

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
	public Class<? extends Attribute> getAttributeType() {
		return Collection.class;
	}
	
}
