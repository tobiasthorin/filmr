package filmr.helpers.exceptions.booking;

import filmr.helpers.exceptions.FilmrBaseException;
import filmr.helpers.exceptions.FilmrErrorCode;

/**
 * Created by Marco on 2016-05-23.
 */
public class FilmrBookingPastDateException extends FilmrBaseException{

	public FilmrBookingPastDateException() {
		super(FilmrErrorCode.F425);
	}



}
