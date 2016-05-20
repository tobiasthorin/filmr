package filmr.helpers.exceptions;

public class FilmrPOSTRequestWithPredefinedIdException extends FilmrBaseException{
	private static final long serialVersionUID = 4088723849734680557L;

	public FilmrPOSTRequestWithPredefinedIdException(String msg) {
		super(msg, FilmrErrorCode.F105);
	}

}
