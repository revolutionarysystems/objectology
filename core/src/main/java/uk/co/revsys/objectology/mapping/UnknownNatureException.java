package uk.co.revsys.objectology.mapping;

public class UnknownNatureException extends DeserialiserException{

    public UnknownNatureException(String nature) {
        super("Unknown nature: " + nature);
    }

}
