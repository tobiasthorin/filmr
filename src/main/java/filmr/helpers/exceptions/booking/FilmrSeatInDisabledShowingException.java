package filmr.helpers.exceptions.booking;

import filmr.helpers.exceptions.FilmrBaseException;
import filmr.helpers.exceptions.FilmrErrorCode;

public class FilmrSeatInDisabledShowingException extends FilmrBaseException {
 static final long serialVersionUID = -8549150898632876878L;

	public FilmrSeatInDisabledShowingException() {
		super(FilmrErrorCode.F420.getDescription(), FilmrErrorCode.F420);
	}
}
