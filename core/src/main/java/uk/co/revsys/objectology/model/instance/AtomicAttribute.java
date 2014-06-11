package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.annotation.JsonValue;
import java.text.ParseException;

public abstract class AtomicAttribute<V extends Object> extends AbstractAttribute{

	private V value;

	public AtomicAttribute() {
	}

	public AtomicAttribute(V value) {
		this.value = value;
	}
    
    public AtomicAttribute(String value) throws ParseException{
        setValueFromString(value);
    }

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
    
    public abstract void setValueFromString(String value) throws ParseException;

    @Override
    @JsonValue
    public String toString() {
        V value = getValue();
        if(value == null){
            return "";
        }
        return getValue().toString();
    }
	
}
