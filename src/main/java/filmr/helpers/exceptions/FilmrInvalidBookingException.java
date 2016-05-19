package filmr.helpers.exceptions;


public class FilmrInvalidBookingException extends FilmrBaseException {
	private static final long serialVersionUID = 1262004219111251879L;

	public FilmrInvalidBookingException(String msg, FilmrStatusCode filmrStatusCode) {
		super(msg, filmrStatusCode);
		System.out.println("Inside FilmrInvalidBookingException");
	}

	
}
