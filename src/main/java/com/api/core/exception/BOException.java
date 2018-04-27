package com.api.core.exception;

import java.util.ArrayList;
import java.util.List;

import com.api.core.util.Message;

public class BOException extends Exception {
	
	private static final long serialVersionUID = 5975724819878450751L;
	
	List<Message> errorMessages = new ArrayList<>();

	public BOException() {
		super();
	}
	
	public BOException(String message) {
		super(message);
		Message errorMessage = new Message(message);
		this.errorMessages.add(errorMessage);
	}
	
	public BOException(List<Message> errorMessage) {
		super(errorMessage.toString());
		this.errorMessages.addAll(errorMessage);
	}

	public List<Message> getErrorMessages() {
		return errorMessages;
	}
}
