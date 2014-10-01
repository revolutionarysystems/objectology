package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Time;

public class TimeTemplate extends AtomicAttributeTemplate<Time> {

    @Override
    public Class<? extends Time> getAttributeType() {
        return Time.class;
    }

    @Override
    public String getNature() {
        return "time";
    }

}
