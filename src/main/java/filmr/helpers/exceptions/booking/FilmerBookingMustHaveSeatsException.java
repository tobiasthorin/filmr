package filmr.helpers.exceptions.booking;

import filmr.helpers.exceptions.FilmrBaseException;
import filmr.helpers.exceptions.FilmrStatusCode;

public class FilmerBookingMustHaveSeatsException extends FilmrBaseException{

	private static final long serialVersionUID = -6436836541246387L;

	public FilmerBookingMustHaveSeatsException() {
		super(FilmrStatusCode.F400.getDescription(), FilmrStatusCode.F400);
	}

}
