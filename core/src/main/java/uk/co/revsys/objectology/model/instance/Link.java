package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.json.serialise.LinkSerialiser;
import uk.co.revsys.objectology.model.PersistedObject;
import uk.co.revsys.objectology.model.ReferenceType;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.service.ServiceFactory;

@JsonSerialize(using = LinkSerialiser.class)
public class Link extends AbstractLink<Link, LinkTemplate> {

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

    @Override
	public PersistedObject getAssociatedObject() throws DaoException {
		ReferenceType referenceType = getTemplate().getReferenceType();
		String associatedType = getTemplate().getAssociatedType();
		if (referenceType.equals(ReferenceType.id)) {
			if (associatedType.equals("template")) {
				return ServiceFactory.getOlogyTemplateService().findById(getReference());
			} else {
				return ServiceFactory.getOlogyInstanceService().findById(associatedType, getReference());
			}
		} else if (referenceType.equals(ReferenceType.name)) {
			if (associatedType.equals("template")) {
				return ServiceFactory.getOlogyTemplateService().findByName(getReference());
			} else {
				return ServiceFactory.getOlogyInstanceService().findByName(associatedType, getReference());
			}
		} else {
			return null;
		}
	}

    @Override
    public Link copy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
