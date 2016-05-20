package filmr.helpers.exceptions;

public class FilmrShowingTimeOccupiedException extends FilmrBaseException {
	private static final long serialVersionUID = 4024737160777564917L;

	public FilmrShowingTimeOccupiedException(String msg) {
        super(msg, FilmrErrorCode.F303);
    }
}