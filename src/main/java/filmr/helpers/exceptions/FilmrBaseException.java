package filmr.helpers.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Encapsulates the extra exception fields that are common to all Filmr exceptions. Made to resemble standard error message, but with additional fields.
 * @see FilmrExceptionModel
 */
public class FilmrBaseException extends Exception{
	private static final long serialVersionUID = 3969845334183699317L;
	
	private final FilmrErrorCode filmrStatusCode;
	private final HttpStatus httpStatus;
	
	public FilmrBaseException(String msg, FilmrErrorCode filmrStatusCode) {
		super(msg);
		this.filmrStatusCode = filmrStatusCode;
		this.httpStatus = filmrStatusCode.getHttpStatus();
    }
	
	public FilmrBaseException(FilmrErrorCode filmrErrorCode) {
		this(filmrErrorCode.getDescription(), filmrErrorCode);
	}

	public FilmrErrorCode getFilmrStatusCode() {
		return filmrStatusCode;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	

}
