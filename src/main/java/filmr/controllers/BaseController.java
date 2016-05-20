package filmr.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import filmr.helpers.exceptions.FilmrBaseException;
import filmr.helpers.exceptions.FilmrExceptionModel;

/**
 * 
 * Provides shared logging capabilities and error handling for Filmr custom errors 
 * for all controllers extending the class. 
 *
 */
public class BaseController {
	
	protected org.apache.log4j.Logger logger = Logger.getLogger(this.getClass());
	
    // all custom errors should inherit from FilmrBaseException, so this should work for all of them. 
    @ExceptionHandler(FilmrBaseException.class)
    @ResponseBody
    public ResponseEntity<FilmrExceptionModel> handleBadRequest(HttpServletRequest req, FilmrBaseException ex) {
    	logger.debug("Catching custom error in method inherrited from BaseController.java ");
    	FilmrExceptionModel exceptionModel = new FilmrExceptionModel(req, ex);
        return new ResponseEntity<FilmrExceptionModel>(exceptionModel, ex.getHttpStatus());
    }

}
