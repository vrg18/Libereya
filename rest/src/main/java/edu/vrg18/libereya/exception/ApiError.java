package edu.vrg18.libereya.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@JsonInclude(Include.NON_NULL)
public class ApiError {
	
	private HttpStatus status;
	private String message;
	private String debugMessage;
	private List<FieldValidationError> fieldValidationErrors;

	ApiError() {
	}

	ApiError(HttpStatus status) {
		this();
		this.status = status;
	}

	ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.setDebugMessage(ex.getMessage());
	}

	ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.setDebugMessage(ex.getMessage());
	}

	void addValidationErrors(List<FieldError> fieldErrors) {
		fieldErrors.forEach(error -> {
			FieldValidationError subError = new FieldValidationError();
			subError.setField(error.getField());
			subError.setMessage(error.getDefaultMessage());
			subError.setRejectedValue(error.getRejectedValue());
			subError.setObject(error.getObjectName());
			this.addSubError(subError);
		});
	}

	private void addSubError(FieldValidationError subError) {
		if (fieldValidationErrors == null) {
			fieldValidationErrors = new ArrayList<>();
		}
		fieldValidationErrors.add(subError);
	}

	public List<FieldValidationError> getFieledValidationErrors() {
		return fieldValidationErrors;
	}

}
