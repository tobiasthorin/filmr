package filmr.helpers.exceptions;

public class FilmrInvalidDateFormatException extends FilmrBaseException {
	
	private static final long serialVersionUID = -1680718861456173358L;

	public FilmrInvalidDateFormatException() {
		super("Invalid date format detected. Use ISO-8601 (YYYY-MM-DDTHH:mm:ss.sssZ)", FilmrStatusCode.F900);
	}

}
