package com.api.app.service;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.api.app.bo.PersonBO;
import com.api.app.dto.ListDTO;
import com.api.app.model.Person;
import com.api.core.exception.BOException;
import com.api.core.util.Message;

@Path("/people")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonService {
	
	@Inject
	private PersonBO personBO;
	
	@Inject
	private Logger log;
	
	@GET
	@Path("/{id}")
	public Response find(@PathParam("id") Long id) {
		try {
			Person pessoaFisica = personBO.find(id);
			return Response.status(200).entity(pessoaFisica).build();
	    } catch (BOException e) {
			log.error(e.getMessage());
			return Response.status(400).entity(Message.setMessage(e)).build();
		} catch (PersistenceException e) {
	    	log.error(e.getMessage());
	    	return Response.status(500).entity(Message.setMessage("error.500")).build();
	    }
	}
	
	@GET
	@Path("/")
	public Response list(@QueryParam("page") Integer page, 
			@QueryParam("page-size") Integer pageSize,
			@QueryParam("filter") String filter) {
		try {
			ListDTO<Person> people = personBO.list(page, pageSize, filter);
			return Response.status(200).entity(people).build();
	    } catch (PersistenceException e) {
	    	log.error(e.getMessage());
	    	return Response.status(500).entity(Message.setMessage("error.500")).build();
	    }
	}
	
	@POST
	@Path("/")
	public Response create(Person person) {
		try {
			personBO.create(person);
			return Response.status(200).entity(Message.setMessage("person.create")).build();
		} catch (BOException e) {
			log.error(e.getMessage());
			return Response.status(400).entity(Message.setMessage(e)).build();
		} catch (PersistenceException e) {
	    	log.error(e.getMessage());
	    	return Response.status(500).entity(Message.setMessage("error.500")).build();
	    }
	}
	
	@PUT
	@Path("/")
	public Response update(Person person) {
		try {
			personBO.update(person);
			return Response.status(200).entity(Message.setMessage("person.update")).build();
		} catch (BOException e) {
			log.error(e.getMessage());
			return Response.status(400).entity(Message.setMessage(e)).build();
		} catch (PersistenceException e) {
	    	log.error(e.getMessage());
	    	return Response.status(500).entity(Message.setMessage("error.500")).build();
	    }

	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		try {
			personBO.delete(id);
			return Response.status(200).entity(Message.setMessage("person.delete")).build();
	    } catch (BOException e) {
			log.error(e.getMessage());
			return Response.status(400).entity(Message.setMessage(e)).build();
		} catch (PersistenceException e) {
	    	log.error(e.getMessage());
	    	return Response.status(500).entity(Message.setMessage("error.500")).build();
	    }
	}

}
