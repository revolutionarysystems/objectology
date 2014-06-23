package uk.co.revsys.objectology.mapping;

public class SerialiserException extends Exception{

	public SerialiserException() {
	}

	public SerialiserException(String message) {
		super(message);
	}

	public SerialiserException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerialiserException(Throwable cause) {
		super(cause);
	}

}
