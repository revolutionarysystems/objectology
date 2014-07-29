package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.instance.Sequence;

public class SequenceTemplate extends PropertyTemplate<Sequence>{

    private String name;
    private int length;

    public SequenceTemplate() {
    }

    public SequenceTemplate(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
    
    @Override
    public Class<? extends Sequence> getAttributeType() {
        return Sequence.class;
    }

    @Override
    public String getNature() {
        return "sequence";
    }

}
