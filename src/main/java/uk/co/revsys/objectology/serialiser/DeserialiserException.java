package uk.co.revsys.objectology.serialiser;

public class DeserialiserException extends Exception{

	public DeserialiserException() {
	}

	public DeserialiserException(String message) {
		super(message);
	}

	public DeserialiserException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeserialiserException(Throwable cause) {
		super(cause);
	}

}
