package myApp;

import dao.PeopleDao;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("/people")
public class PersonResource {
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	public Response getPeople(){
	
		List<Person> people = PeopleDao.instance.showAll();
		 GenericEntity<List<Person>> entity = new GenericEntity<List<Person>>(people) {};
		 return Response
	                .status(Response.Status.OK)
	                .entity(entity)
	                .build();
	}
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postPerson(@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("car") String car,
			@FormParam("city") String city,
			@FormParam("country") String country,
			@Context HttpServletResponse servletResponse) throws IOException{
		Person person = new Person(firstName, lastName, car, city, country);
		PeopleDao.instance.addPerson(person);
	}
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("{id}")
	public void putPerson(@PathParam("id") String id,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("car") String car,
			@FormParam("city") String city,
			@FormParam("country") String country,
			@Context HttpServletResponse servletResponse) throws IOException{
		Person person = new Person(id, firstName, lastName, car, city, country);
		PeopleDao.instance.updatePerson(person);
	}
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
	@Path("{id}")
	public Person findPerson(@PathParam("id") String id) throws IOException{
		System.out.print("Delete id = " + id);
		Person person = PeopleDao.instance.getPerson(id);
		return person;
	}
	@DELETE
	@Produces(MediaType.TEXT_HTML)
	@Path("{id}")
	public void getPerson(@PathParam("id") String id) throws IOException{
		System.out.print("Delete id = " + id);
		PeopleDao.instance.deleteAccount(id);
	}
	
	

}
