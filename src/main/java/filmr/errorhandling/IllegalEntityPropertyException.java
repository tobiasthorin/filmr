package filmr.errorhandling;

public class IllegalEntityPropertyException extends Exception {
	private static final long serialVersionUID = 2071745177858992856L;

	public IllegalEntityPropertyException(String message) {
		super(message);
	}
}
