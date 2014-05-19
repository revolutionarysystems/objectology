package uk.co.revsys.objectology.model.instance;

public class AtomicAttribute extends AbstractAttribute{

	private String value;

	public AtomicAttribute() {
	}

	public AtomicAttribute(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
