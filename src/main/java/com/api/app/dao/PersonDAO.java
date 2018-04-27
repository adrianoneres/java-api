package com.api.app.dao;

import com.api.app.model.Person;
import com.api.core.base.DAO;

public class PersonDAO extends DAO<Person> {
	
	public PersonDAO() {
		super(Person.class);
	}
}
