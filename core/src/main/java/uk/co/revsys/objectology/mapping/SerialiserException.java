package uk.co.revsys.objectology.mapping;

import java.io.IOException;

public class SerialiserException extends IOException{

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
