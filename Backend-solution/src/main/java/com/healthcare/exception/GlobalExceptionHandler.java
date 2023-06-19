package com.healthcare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
		ExceptionResponse exResponse = new ExceptionResponse(ex.getMessage(), System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DoctorNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(DoctorNotFoundException ex) {
		ExceptionResponse exResponse = new ExceptionResponse(ex.getMessage(), System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(HospitalNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(HospitalNotFoundException ex) {
		ExceptionResponse exResponse = new ExceptionResponse(ex.getMessage(), System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PatientNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(PatientNotFoundException ex) {
		ExceptionResponse exResponse = new ExceptionResponse(ex.getMessage(), System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.NOT_FOUND);
	}
}