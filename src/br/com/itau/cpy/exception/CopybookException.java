package br.com.itau.cpy.exception;

public class CopybookException extends Exception {

	private static final long serialVersionUID = 7127260728362825795L;

	public CopybookException(String message) {
		super(message);
	}

	public CopybookException(Throwable cause) {
		super(cause);
	}

	public CopybookException(String message, Throwable cause) {
		super(message, cause);
	}
}
