package filmr.helpers.exceptions;

public class FilmrPUTRequestWithMissingEntityIdException extends FilmrBaseException {
	private static final long serialVersionUID = 3278807783949866802L;

	public FilmrPUTRequestWithMissingEntityIdException(String msg) {
		super(msg, FilmrErrorCode.F110);
	}

}
