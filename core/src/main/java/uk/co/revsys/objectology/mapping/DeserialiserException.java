package uk.co.revsys.objectology.mapping;

import java.io.IOException;

public class DeserialiserException extends IOException{

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
