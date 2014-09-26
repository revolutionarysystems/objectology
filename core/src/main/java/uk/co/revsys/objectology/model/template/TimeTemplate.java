package uk.co.revsys.objectology.model.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.Time;

public class TimeTemplate extends AtomicAttributeTemplate<Time> {

    private Time value;
    @JsonProperty(value = "value")
    private String rawValue;

    @Override
    @JsonIgnore
    public void setValue(Time value) {
        super.setValue(value);
        this.value = value;
    }

    @Override
    @JsonIgnore
    public Time getValue() {
        if (value != null) {
            return value;
        }
        if(rawValue == null){
            return newInstance();
        }
        try {
            return new Time(rawValue);
        } catch (ValidationException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        } catch (ParseException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        }
    }

    public String getRawValue() {
        return rawValue;
    }

    public void setRawValue(String rawValue) throws ValidationException, ParseException {
        this.rawValue = rawValue;
        // To validate the value
        Time time = new Time(rawValue);
    }

    @Override
    public Time newInstance() {
        return new Time();
    }

    @Override
    public Class<? extends Time> getAttributeType() {
        return Time.class;
    }

    @Override
    public String getNature() {
        return "time";
    }

}
