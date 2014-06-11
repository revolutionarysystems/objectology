package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.ArrayList;
import java.util.List;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.serialiser.jackson.CollectionDeserialiser;

@JsonDeserialize(using = CollectionDeserialiser.class)
public class Collection extends AbstractAttribute<CollectionTemplate>{

	private List<Attribute> members = new ArrayList<Attribute>();

    @JsonValue
	public List<Attribute> getMembers() {
		return members;
	}

	public void setMembers(List<Attribute> members) {
		this.members = members;
	}
	
}
