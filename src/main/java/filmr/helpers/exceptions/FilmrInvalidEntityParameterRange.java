package filmr.helpers.exceptions;

public class FilmrInvalidEntityParameterRange extends FilmrBaseException{
	private static final long serialVersionUID = 4088723849734680557L;

	public FilmrInvalidEntityParameterRange(String msg) {
		super(msg, FilmrErrorCode.F115);
	}

}
