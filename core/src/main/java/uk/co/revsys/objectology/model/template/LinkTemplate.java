package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.ReferenceType;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Link;

public class LinkTemplate extends AbstractAttributeTemplate<Link>{

	private ReferenceType referenceType = ReferenceType.id;
	private String associatedType;

	public LinkTemplate() {
	}

	public LinkTemplate(String associatedType) {
		this.associatedType = associatedType;
	}
    
    public LinkTemplate(String associatedType, ReferenceType referenceType) {
		this.associatedType = associatedType;
        this.referenceType = referenceType;
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
    public Link newInstance() {
        return new Link();
    }
	
	@Override
	public Class<? extends Link> getAttributeType() {
		return Link.class;
	}

    @Override
    public String getNature() {
        return "link";
    }

}
