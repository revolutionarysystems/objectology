package uk.co.revsys.objectology.model.instance;

import java.text.ParseException;

public class Property extends AtomicAttribute<String>{

	public Property() {
	}

	public Property(String value) {
		setValue(value);
	}

    @Override
    public void setValueFromString(String value) throws ParseException {
        setValue(value);
    }
	
}
