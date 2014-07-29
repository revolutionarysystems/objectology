package uk.co.revsys.objectology.model.instance;

import java.math.BigDecimal;
import java.text.ParseException;

public class Measurement extends AtomicAttribute<BigDecimal> {

    public Measurement() {
    }

    public Measurement(BigDecimal value) {
        super(value);
    }

    public Measurement(String value) throws ParseException {
        super(value);
    }
    
    public Measurement(int value){
        super(new BigDecimal(value));
    }
    
    public Measurement(float value){
        super(new BigDecimal(value));
    }
    
    public Measurement(double value){
        super(new BigDecimal(value));
    }

    @Override
    public void setValueFromString(String value) throws ParseException {
        if (value != null && !value.isEmpty()) {
            setValue(new BigDecimal(value));
        }
    }

}
