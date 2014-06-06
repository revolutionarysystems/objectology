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

    @Override
    public void setValueFromString(String value) throws ParseException {
        if (value != null && !value.isEmpty()) {
            System.out.println("value = " + value);
            setValue(new BigDecimal(value));
        }
    }

}
