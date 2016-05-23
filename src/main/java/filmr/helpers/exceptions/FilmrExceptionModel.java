package filmr.helpers.exceptions;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 
 * Class to represent custom errors and their attributes. 
 * This is the object that will be serialized to json and presented to the front-end developer getting a custom error.
 * 
 * @see FilmrBaseException
 */
public class FilmrExceptionModel {
	
	public final String timestamp;
	public final String status;
	public final String error;
	public final String exception;
	public final String message;
	public final String path;
	public final String filmrErrorCode;
	public final String filmrErrorDescription;

    public FilmrExceptionModel(HttpServletRequest req, FilmrBaseException filmrException) {
    	
    	this.timestamp = LocalDateTime.now().toString();
    	this.status = filmrException.getHttpStatus().toString();
    	this.error = filmrException.getHttpStatus().getReasonPhrase();
        this.path = req.getRequestURL().toString();
        this.exception = filmrException.getClass().getSimpleName();
        this.message = filmrException.getLocalizedMessage();
        this.filmrErrorCode = filmrException.getFilmrStatusCode().toString();
        this.filmrErrorDescription = filmrException.getFilmrStatusCode().getDescription();
    }
    
}
