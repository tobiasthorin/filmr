package filmr.helpers.exceptions;

import org.springframework.http.HttpStatus;

public class FilmrBaseException extends Exception{
	private static final long serialVersionUID = 3969845334183699317L;
	
	private final FilmrStatusCode filmrStatusCode;
	private final HttpStatus httpStatus;
	
	public FilmrBaseException(String msg, FilmrStatusCode filmrStatusCode) {
		super(msg);
		this.filmrStatusCode = filmrStatusCode;
		this.httpStatus = filmrStatusCode.getHttpStatus();
    }

	public FilmrStatusCode getFilmrStatusCode() {
		return filmrStatusCode;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	

}
