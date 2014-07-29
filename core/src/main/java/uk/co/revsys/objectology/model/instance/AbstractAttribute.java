package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.revsys.objectology.model.template.AttributeTemplate;

public class AbstractAttribute<T extends AttributeTemplate> implements Attribute<T> {

	private T template;
    private OlogyInstance parent;
	
	@Override
	public void setTemplate(T template) {
		this.template = template;
	}

	@Override
    @JsonIgnore
	public T getTemplate() {
		return template;
	}

    @Override
    @JsonIgnore
    public OlogyInstance getParent() {
        return parent;
    }

    @Override
    public void setParent(OlogyInstance parent) {
        this.parent = parent;
    }

}
