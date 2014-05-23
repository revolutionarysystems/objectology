package uk.co.revsys.objectology.model.instance;

import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.ReferenceType;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.service.OlogyObjectServiceFactory;

public class Link extends AbstractAttribute<LinkTemplate>{

	private String reference;

	public Link(String reference) {
		this.reference = reference;
	}

	public Link() {
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public OlogyInstance getAssociatedObject() throws DaoException{
		ReferenceType referenceType = getTemplate().getReferenceType();
		if(referenceType.equals(ReferenceType.id)){
			return OlogyObjectServiceFactory.getOlogyInstanceService().findById(getTemplate().getAssociatedType(), getReference());
		}else if(referenceType.equals(ReferenceType.name)){
			return OlogyObjectServiceFactory.getOlogyInstanceService().findByName(getTemplate().getAssociatedType(), getReference());
		}else{
			return null;
		}
	}
	
}
