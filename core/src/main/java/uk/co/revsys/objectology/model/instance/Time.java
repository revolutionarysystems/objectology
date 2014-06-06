package uk.co.revsys.objectology.model.instance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.time.DateUtils;

public class Time extends AtomicAttribute<Date> {

    private String[] dateFormats = new String[]{"dd/MM/yyyy", "dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy HH:mm:ss Z", "yyyy-MM-dd", "dd/MM/yyyy'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ssZ"};
    private SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public Time() {
    }

    public Time(String value) throws ParseException {
        setValueFromString(value);
    }

    public Time(Date value) {
        super(value);
    }

    @Override
    public void setValueFromString(String value) throws ParseException {
        if (value != null && !value.isEmpty()) {
            if (value.equals("now")) {
                setValue(new Date());
            }else if(value.equals("ff")){
                setValue(new Date(9999, 11, 31, 23, 59, 59));
            }else {
                setValue(DateUtils.parseDate(value, dateFormats));
            }
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

}
