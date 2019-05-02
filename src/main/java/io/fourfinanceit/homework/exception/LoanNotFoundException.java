package io.fourfinanceit.homework.exception;

public class LoanNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LoanNotFoundException(Long id) {
		super("Could not find loan with id " + id);
	}
}
