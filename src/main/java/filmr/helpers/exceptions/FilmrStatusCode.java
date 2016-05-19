package filmr.helpers.exceptions;

import org.springframework.http.HttpStatus;

public enum FilmrStatusCode {
	
	// for all entities
	F101(HttpStatus.PRECONDITION_FAILED, "POST request must not include entity with a set (non-null) id value"), 
	// bookings
	F400(HttpStatus.PRECONDITION_FAILED, "Booking must include at least one seat."),
	F410(HttpStatus.PRECONDITION_FAILED, "Seat to book must not have state DISABLED or NOT_A_SEAT, and must must exist in theater specified in the choosen showing"), 
	F415(HttpStatus.PRECONDITION_FAILED, "Seat to book must not already be booked."),
	F420(HttpStatus.PRECONDITION_FAILED, "Seat to book must not be for a showing that is disabled."),
	F430(HttpStatus.CONFLICT, "Conflict... "),
	
	// showings
	
	F303(HttpStatus.PRECONDITION_FAILED, "Showing collided with existing showing(s)"),
	
	// Misc.
	F900(HttpStatus.BAD_REQUEST, "Invalid date format detected. Use ISO-8601 (YYYY-MM-DDTHH:mm:ss)")
	
	; // end of enum declaration
	
	private HttpStatus httpStatus;
	private String description;
	
	//constructor
	FilmrStatusCode(HttpStatus httpStatus, String description) {
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
