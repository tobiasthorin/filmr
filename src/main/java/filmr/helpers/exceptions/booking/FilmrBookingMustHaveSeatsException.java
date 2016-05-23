package filmr.helpers.exceptions.booking;

import filmr.helpers.exceptions.FilmrBaseException;
import filmr.helpers.exceptions.FilmrErrorCode;

public class FilmrBookingMustHaveSeatsException extends FilmrBaseException{

	private static final long serialVersionUID = -6436836541246387L;

	public FilmrBookingMustHaveSeatsException() {
		super(FilmrErrorCode.F400.getDescription(), FilmrErrorCode.F400);
	}

}
