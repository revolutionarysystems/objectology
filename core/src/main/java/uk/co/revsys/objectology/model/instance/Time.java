package uk.co.revsys.objectology.model.instance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.time.DateUtils;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.template.TimeTemplate;

public class Time extends AtomicAttribute<Time, TimeTemplate, Date> {
    
    private static String[] dateFormats = new String[]{"dd/MM/yyyy", "dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy HH:mm:ss Z", "yyyy-MM-dd", "dd/MM/yyyy'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ssZ"};
    private SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public Time() {
    }

    public Time(String value) throws ValidationException, ParseException {
        super(value);
    }

    public Time(Date value) throws ValidationException {
        super(value);
    }
    
    public Time(long value) throws ValidationException{
        super(new Date(value));
    }

    @Override
    public Date parseValueFromString(String value) throws ParseException {
        if (value == null || value.isEmpty()) {
            return null;
        }
        if (value.equals("now")) {
            return new Date();
        } else if (value.equals("ff")) {
            return new Date(9999, 11, 31, 23, 59, 59);
        } else {
            return DateUtils.parseDate(value, dateFormats);
        }
    }

    @Override
    public String toString() {
        Date value = getValue();
        if (value == null) {
            return "";
        } else {
            return outputFormat.format(value);
        }
    }

    public static Time getNow() {
        try {
            return new Time("now");
        } catch (ValidationException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        } catch (ParseException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        }
    }

    public static Time getFarFuture() {
        try {
            return new Time("ff");
        } catch (ValidationException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        } catch (ParseException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        }
    }

}
