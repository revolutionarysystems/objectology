package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.mapping.json.deserialise.CollectionDeserialiser;

@JsonDeserialize(using = CollectionDeserialiser.class)
public class Collection<M extends Attribute> extends AbstractAttribute<CollectionTemplate>{

	private List<M> members = new ArrayList<M>();

    public Collection() {
    }

    @JsonValue
	public List<M> getMembers() {
		return Collections.unmodifiableList(members);
	}

	public void setMembers(List<M> members) {
		this.members = members;
	}
    
    public void add(M member){
        members.add(member);
    }
    
    public boolean contains(M member){
        return members.contains(member);
    }
    
    public boolean remove(M member){
        return members.remove(member);
    }
    
    public int size(){
        return members.size();
    }
	
}
