package uk.co.revsys.objectology.model.instance;

import java.math.BigDecimal;
import java.text.ParseException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;

public class Measurement extends AtomicAttribute<Measurement, MeasurementTemplate, BigDecimal> {

    public Measurement() {
    }

    public Measurement(BigDecimal value) throws ValidationException {
        super(value);
    }

    public Measurement(String value) throws ValidationException, ParseException {
        super(value);
    }

    public Measurement(int value) throws ValidationException {
        super(new BigDecimal(value));
    }

    public Measurement(float value) throws ValidationException {
        super(new BigDecimal(value));
    }

    public Measurement(double value) throws ValidationException {
        super(new BigDecimal(value));
    }

    @Override
    public BigDecimal parseValueFromString(String value) throws ParseException {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return new BigDecimal(value);
    }

    @Override
    public Measurement copy() {
        try {
            return new Measurement(getValue());
        } catch (ValidationException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (getValue() == null) {
            return obj == null;
        }
        if (obj instanceof Measurement) {
            obj = ((Measurement) obj).getValue();
        } else if (obj instanceof Integer) {
            obj = new BigDecimal((int) obj);
        } else if (obj instanceof Float) {
            obj = new BigDecimal((float) obj);
        }
        return getValue().equals(obj);
    }

}
