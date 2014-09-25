package uk.co.revsys.objectology.model.instance;

import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.mapping.json.JSONNullType;

@JSONNullType("\"\"")
public class Sequence extends Property{

    public Sequence() {
    }
    
    public Sequence(String value) throws ValidationException{
        super(value);
    }

}
