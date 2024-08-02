package broker.twotier.exception;

public class DuplicateSSNException extends Exception {
	public DuplicateSSNException(String message) {
		super(message);
	}
	public DuplicateSSNException() {
		this("This is DuplicateSSN...");
	}
}
