package filmr.helpers.exceptions.booking;

import filmr.helpers.exceptions.FilmrBaseException;
import filmr.helpers.exceptions.FilmrStatusCode;

public class FilmrSeatInDisabledShowingException extends FilmrBaseException {
 static final long serialVersionUID = -8549150898632876878L;

	public FilmrSeatInDisabledShowingException() {
		super(FilmrStatusCode.F420.getDescription(), FilmrStatusCode.F420);
	}
}
