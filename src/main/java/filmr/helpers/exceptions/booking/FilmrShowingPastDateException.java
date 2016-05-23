package filmr.helpers.exceptions.booking;

import filmr.helpers.exceptions.FilmrBaseException;
import filmr.helpers.exceptions.FilmrErrorCode;

/**
 * Created by Marco on 2016-05-23.
 */
public class FilmrShowingPastDateException  extends FilmrBaseException{

	public FilmrShowingPastDateException() {
		super(FilmrErrorCode.F425);
	}



}
