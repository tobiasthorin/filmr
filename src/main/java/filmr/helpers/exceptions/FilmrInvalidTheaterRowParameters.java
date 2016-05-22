package filmr.helpers.exceptions;

public class FilmrInvalidTheaterRowParameters extends FilmrBaseException {

	private static final long serialVersionUID = -5267670005530915172L;

	public FilmrInvalidTheaterRowParameters(String msg) {
		super(msg, FilmrErrorCode.F510);
	}

}
