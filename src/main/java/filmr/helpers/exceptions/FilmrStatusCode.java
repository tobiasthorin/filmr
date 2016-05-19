package filmr.helpers.exceptions;

import org.springframework.http.HttpStatus;

public enum FilmrStatusCode {
	
	F422(HttpStatus.PRECONDITION_FAILED, "Invalid booking"), 
	F200(HttpStatus.PRECONDITION_FAILED, "POST request must not include entity with a set id value"), 
	F205(HttpStatus.CONFLICT, "Conflict... ")  
	
	; // end of enum declaration
	
	private HttpStatus httpStatus;
	private String description;
	
	//constructor
	FilmrStatusCode(HttpStatus httpStatus, String description) {
		this.httpStatus = httpStatus;
	}
	
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	public String getDescription() {
		return description;
	}
	
	
}
