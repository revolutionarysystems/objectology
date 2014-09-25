package uk.co.revsys.objectology.model.instance;

import java.math.BigDecimal;
import java.text.ParseException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;

public class Measurement extends AtomicAttribute<MeasurementTemplate, BigDecimal> {

    public Measurement() {
    }
    
    public Measurement(BigDecimal value) throws ValidationException {
        super(value);
    }

    public Measurement(String value) throws ValidationException, ParseException {
        super(value);
    }
    
    public Measurement(int value) throws ValidationException{
        super(new BigDecimal(value));
    }
    
    public Measurement(float value) throws ValidationException{
        super(new BigDecimal(value));
    }
    
    public Measurement(double value) throws ValidationException{
        super(new BigDecimal(value));
    }

    @Override
    public BigDecimal parseValueFromString(String value) throws ParseException {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return new BigDecimal(value);
    }

}
