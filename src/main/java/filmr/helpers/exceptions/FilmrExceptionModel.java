package filmr.helpers.exceptions;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

public class FilmrExceptionModel {
	
	public final String timestamp;
	public final String status;
	public final String error;
	public final String exception;
	public final String message;
	public final String path;
	public final String filmrErrorCode;

    public FilmrExceptionModel(HttpServletRequest req, FilmrBaseException exception) {
    	this.timestamp = LocalDateTime.now().toString();
    	this.status = exception.getHttpStatus().toString();//req.getHeader("status"); // TODO: 
    	this.error = exception.getHttpStatus().getReasonPhrase();
        this.path = req.getRequestURL().toString();
        
        String[] splitExceptionName = exception.getClass().getName().split(".");
        int indexOfActualName = splitExceptionName.length -1;
        String exceptionName = splitExceptionName[indexOfActualName];
        
        this.exception = exceptionName;
        this.message = exception.getLocalizedMessage();
        this.filmrErrorCode = exception.getFilmrStatusCode().toString();
    }
    
//    {
//    	  "timestamp": "2016-05-19T09:44:20.001+0000",
//    	  "status": 400,
//    	  "error": "Bad Request",
//    	  "exception": "org.springframework.web.bind.MissingServletRequestParameterException",
//    	  "message": "Required Integer parameter 'number_of_rows' is not present",
//    	  "path": "/filmr/api/theaters/"
//    	}
    
    
}
