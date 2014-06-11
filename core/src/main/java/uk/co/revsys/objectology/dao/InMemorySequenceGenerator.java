package uk.co.revsys.objectology.dao;

public class InMemorySequenceGenerator extends AbstractSequenceGenerator{

    private int seq = 0;

    @Override
    public String doGetNextSequence(String name) throws SequenceException {
        seq = seq + 1;
        return String.valueOf(seq);
    }
    
}
