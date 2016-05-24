package filmr.helpers.exceptions;

/**
 * Created by Marco on 2016-05-24.
 */
public class FilmrPastDateException extends FilmrBaseException {
	public FilmrPastDateException() {super(FilmrErrorCode.F306);}
}
