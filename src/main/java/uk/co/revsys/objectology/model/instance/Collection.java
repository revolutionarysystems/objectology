package uk.co.revsys.objectology.model.instance;

import java.util.ArrayList;
import java.util.List;

public class Collection extends AbstractAttribute{

	private List<Attribute> members = new ArrayList<Attribute>();

	public List<Attribute> getMembers() {
		return members;
	}

	public void setMembers(List<Attribute> members) {
		this.members = members;
	}
	
}
