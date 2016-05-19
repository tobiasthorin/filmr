package filmr.helpers.exceptions.booking;

import filmr.helpers.exceptions.FilmrBaseException;
import filmr.helpers.exceptions.FilmrErrorCode;

public class FilmrSeatAlreadyBookedException extends FilmrBaseException {
	private static final long serialVersionUID = 1262004219111251879L;

	public FilmrSeatAlreadyBookedException(String msg) {
		super(msg, FilmrErrorCode.F415);
		System.out.println("Inside FilmrInvalidBookingException");
	}
}
