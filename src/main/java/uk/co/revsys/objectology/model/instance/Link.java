package uk.co.revsys.objectology.model.instance;

import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.OlogyObject;
import uk.co.revsys.objectology.model.ReferenceType;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.service.OlogyObjectServiceFactory;

public class Link extends AbstractAttribute<LinkTemplate> {

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

	public OlogyObject getAssociatedObject() throws DaoException {
		ReferenceType referenceType = getTemplate().getReferenceType();
		String associatedType = getTemplate().getAssociatedType();
		if (referenceType.equals(ReferenceType.id)) {
			if (associatedType.equals("template")) {
				return OlogyObjectServiceFactory.getOlogyTemplateService().findById(getReference());
			} else {
				return OlogyObjectServiceFactory.getOlogyInstanceService().findById(associatedType, getReference());
			}
		} else if (referenceType.equals(ReferenceType.name)) {
			if (associatedType.equals("template")) {
				return OlogyObjectServiceFactory.getOlogyTemplateService().findByName(getReference());
			} else {
				return OlogyObjectServiceFactory.getOlogyInstanceService().findByName(associatedType, getReference());
			}
		} else {
			return null;
		}
	}

}
