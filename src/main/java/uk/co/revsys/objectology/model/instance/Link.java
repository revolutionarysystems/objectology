package uk.co.revsys.objectology.model.instance;

public class Link implements Attribute{

	private String id;

	public Link(String id) {
		this.id = id;
	}

	public Link() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
