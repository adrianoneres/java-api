package com.api.core.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.api.core.exception.BOException;
import com.api.core.util.Message;

public class BO<T> {
	
	protected Validator validator;
	
	public BO() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	public boolean isValid(T object) {
		Set<ConstraintViolation<T>> validationErrors = validator.validate(object);
		
		return validationErrors.size() == 0;
	}
	
	public void throwsValidationErrors(T obj) throws BOException {
		Set<ConstraintViolation<T>> validationErrors = validator.validate(obj);
		
		List<Message> validationMessages = new ArrayList<>();
		Iterator<ConstraintViolation<T>> erros = validationErrors.iterator();
		while (erros.hasNext()) {
			Message errorMessage = new Message(erros.next().getMessage());
			validationMessages.add(errorMessage);
		}
		
		throw new BOException(validationMessages);
	}
}
