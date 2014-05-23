package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.ReferenceType;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Link;

public class LinkTemplate extends AbstractAttributeTemplate{

	private ReferenceType referenceType = ReferenceType.id;
	private String associatedType;

	public LinkTemplate() {
	}

	public LinkTemplate(String associatedType) {
		this.associatedType = associatedType;
	}
	
	public String getAssociatedType() {
		return associatedType;
	}

	public void setAssociatedType(String associatedType) {
		this.associatedType = associatedType;
	}

	public ReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(ReferenceType referenceType) {
		this.referenceType = referenceType;
	}
	
	@Override
	public Class<? extends Attribute> getAttributeType() {
		return Link.class;
	}

}
