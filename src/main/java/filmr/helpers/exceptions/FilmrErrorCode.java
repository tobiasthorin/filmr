package filmr.helpers.exceptions;

import org.springframework.http.HttpStatus;

/**
 * 
 *	Enum representing different error codes used, by front-end developers, to identity what
 *	went wrong and how/if it should be presented to the end-user.
 *	@see FilmrBaseException
 *
 */
public enum FilmrErrorCode {
	
	// ERROR CODES
	
	// for all entities
	F105(HttpStatus.PRECONDITION_FAILED, "POST request must not include entity with a set (non-null) id value"),
	F110(HttpStatus.PRECONDITION_FAILED, "PUT request must not include entity with an unset (null) id value"),
	F115(HttpStatus.PRECONDITION_FAILED, "Entity parameter is out of range"),
	
	// showings
	
	F303(HttpStatus.PRECONDITION_FAILED, "Showing collided with existing showing(s)"),
	
	// bookings
	F400(HttpStatus.PRECONDITION_FAILED, "Booking must include at least one seat."),
	F410(HttpStatus.PRECONDITION_FAILED, "Seat to book must not have state DISABLED or NOT_A_SEAT, and must must exist in theater specified in the choosen showing"), 
	F415(HttpStatus.PRECONDITION_FAILED, "Seat to book must not already be booked."),
	F420(HttpStatus.PRECONDITION_FAILED, "Seat to book must not be for a showing that is disabled."),
	F425(HttpStatus.PRECONDITION_FAILED, "Showing to book must not be in the past"),
	
	// theaters
	
	F510(HttpStatus.PRECONDITION_FAILED, "Valid row width is 1-64 seats. Valid number of rows is 1-64 rows."),
	
	// other.
	F900(HttpStatus.BAD_REQUEST, "Invalid date format detected. Use YYYY-MM-DDTHH:mm:ss")
	
	; // end of enum declaration
	
	private HttpStatus httpStatus;
	private String description;
	
	//constructor
	FilmrErrorCode(HttpStatus httpStatus, String description) {
		this.httpStatus = httpStatus;
		this.description = description;
	}
	
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	public String getDescription() {
		return description;
	}
	
	
}
