//package filmr.errorhandling;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
////TODO: figure out why this doesn't get called
//@ControllerAdvice
//@EnableWebMvc
//public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//	
//    @ExceptionHandler(value = {IllegalEntityPropertyException.class})
//    public ResponseEntity<Object> handleConflict(RuntimeException exception, WebRequest request) {
//    	
//        // create a body for our response entity,
//    	System.out.println("in RestResponseEntityExceptionHandler. runtime e is: " + exception);
//    	
//    	String bodyOfResponse = 
//        		"Error thrown from class " + exception.getClass().getName() + 
//        		" with message '" + exception.getMessage() + "'";
//        return handleExceptionInternal(exception, bodyOfResponse, 
//          new HttpHeaders(), HttpStatus.CONFLICT, request);
//    }
//	
//	
//}
