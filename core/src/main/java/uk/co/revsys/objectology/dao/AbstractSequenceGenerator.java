package uk.co.revsys.objectology.dao;

import org.apache.commons.lang.StringUtils;

public abstract class AbstractSequenceGenerator implements SequenceGenerator{

    @Override
    public String getNextSequence(String name) throws SequenceException{
        return getNextSequence(name, 0);
    }

    @Override
    public String getNextSequence(String name, int length) throws SequenceException{
        String result = doGetNextSequence(name);
        return StringUtils.leftPad(result, length, "0");
    }
    
    public abstract String doGetNextSequence(String name) throws SequenceException;

}
