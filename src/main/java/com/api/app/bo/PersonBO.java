package com.api.app.bo;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;

import com.api.app.dao.PersonDAO;
import com.api.app.dto.ListDTO;
import com.api.app.model.Person;
import com.api.core.base.BO;
import com.api.core.exception.BOException;

public class PersonBO extends BO<Person> {
	
	@Inject
	private PersonDAO personDAO;
	
	@Inject
	private Logger log;
	
	public Person find(Long id) throws BOException {
		log.info("Searching for person with id: " + id);
		
		Person person = personDAO.find(id);
		
		if (person == null) throw new BOException("error.registerNotFound");
		
		return person;
	}
	
	public ListDTO<Person> list(Integer page, Integer pageSize, String filter) {
		if (page == null) page = 1;
		if (pageSize == null) pageSize = 10;
		
		log.info("Listing people. Page " + page + " of " + pageSize);
		
		long total = personDAO.getTotal(filter, "name", "birth", "registration");
		List<Person> data = personDAO.list(page, pageSize, "name", filter, "name", "birth", "registration");
		
		ListDTO<Person> result = new ListDTO<>(total, data);
		
		return result;
	}
	
	@Transactional
	public void create(Person person) throws BOException {
		log.info("Adding person: " + person);
		
		boolean invalidPerson = !isValid(person);
		if (invalidPerson) throwsValidationErrors(person);
		 
		personDAO.create(person);
	}
	
	@Transactional
	public void update(Person person) throws BOException {
		log.info("Updating person: " + person);
		
		boolean PersonInvalida = !isValid(person);
		if (PersonInvalida) throwsValidationErrors(person);
		 
		personDAO.update(person);
		
	}
	
	@Transactional
	public void delete(Long id) throws BOException {
		log.info("Removing person with id: " + id);
		
		Person person = find(id);
		
		personDAO.delete(person);
	}
}
