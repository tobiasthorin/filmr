package filmr.helpers.exceptions;

public class FilmrInvalidDateFormatException extends FilmrBaseException {
	
	private static final long serialVersionUID = -1680718861456173358L;

	public FilmrInvalidDateFormatException() {
		super(FilmrErrorCode.F900);
	}

}
