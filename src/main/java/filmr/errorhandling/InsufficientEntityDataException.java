package filmr.errorhandling;

public class InsufficientEntityDataException extends Exception {
	private static final long serialVersionUID = -8933605710409469345L;

	public InsufficientEntityDataException(String message) {
		super(message);
	}
}
