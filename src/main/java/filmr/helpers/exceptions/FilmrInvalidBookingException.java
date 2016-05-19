package filmr.helpers.exceptions;

public class FilmrInvalidBookingException extends Exception {
	private static final long serialVersionUID = 1262004219111251879L;

	public FilmrInvalidBookingException(String msg) {
        super(msg);
    }
}