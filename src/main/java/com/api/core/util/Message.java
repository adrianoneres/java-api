package com.api.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.api.core.exception.BOException;

public class Message {
	
	private String content;

	public Message(String key) {
		try {
			Properties props = new Properties();
			props.load(this.getClass().getResourceAsStream("/msg.properties"));
			this.content = props.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static List<Message> setMessage(String key) {
		List<Message> messages = new ArrayList<>();
		messages.add(new Message(key));
		
		return messages;
	}
	
	public static List<Message> setMessage(BOException e) {
		List<Message> messages = new ArrayList<>();
		if (e.getErrorMessages() != null) {
			for (Message message : e.getErrorMessages()) {
				messages.add(message);
			}
		}
		
		return messages;
	}

	@Override
	public String toString() {
		return "{'content':'" + content + "'}";
	}
}
