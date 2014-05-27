package uk.co.revsys.objectology.view;

public class ViewNotFoundException extends Exception{

	public ViewNotFoundException() {
	}

	public ViewNotFoundException(String message) {
		super(message);
	}

	public ViewNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ViewNotFoundException(Throwable cause) {
		super(cause);
	}

}
