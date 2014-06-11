package uk.co.revsys.objectology.dao;

public interface SequenceGenerator {

    public String getNextSequence(String name) throws SequenceException;
    
    public String getNextSequence(String name, int length) throws SequenceException;
    
}
