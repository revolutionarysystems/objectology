package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Sequence;

public class SequenceTemplate extends PropertyTemplate{

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
    public Class<? extends Attribute> getAttributeType() {
        return Sequence.class;
    }

}
