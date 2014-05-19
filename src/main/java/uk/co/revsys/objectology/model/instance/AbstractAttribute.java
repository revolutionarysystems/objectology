package uk.co.revsys.objectology.model.instance;

import uk.co.revsys.objectology.model.template.AttributeTemplate;

public class AbstractAttribute<T extends AttributeTemplate> implements Attribute<T> {

	private T template;
	
	@Override
	public void setTemplate(T template) {
		this.template = template;
	}

	@Override
	public T getTemplate() {
		return template;
	}

}
