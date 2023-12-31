package rest;

import java.sql.SQLException;

import entity.Person;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.PersonService;

@Path("/person")
public class PersonRest implements RestCode<Person> {

	@Inject
	PersonService pService;
	
	@GET // Responds to a get request from http
	@Produces(MediaType.APPLICATION_JSON) // Defines what this method will produce for the request
	@Path(value = "/{id}") // The path of the request
	@Override
	public Response find(@PathParam("id") Integer id) {
		System.out.println("Started Find: " + id);
		try {
			Person person = pService.find(id);
			if (person == null) {
				System.out.println(id + " Was not found!");

				return Response.status(Response.Status.NOT_FOUND)
						.entity("The id is invalid or the resource could not be found: " + id).build();
			}
			System.out.println("Found: " + id);
			System.out.println(person.toString());			
			return Response.status(Response.Status.OK).entity(person).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Server wasn't checked for FindAll!");
			return Response.status(Response.Status.NOT_FOUND).entity("Could not reach Database").build();
		}

	}
	
	@GET // Responds to a get request from http
	@Produces(MediaType.APPLICATION_JSON) // Defines what this method will produce for the request
	@Path(value = "/findAll") // The path of the request
	@Override
	public Response findAll() {
		System.out.println("Started Find All!");
		try {
			return Response.status(Response.Status.OK).entity(pService.findAll()).build();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).entity("Could not reach Database").build();
		}

	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) // Defines what this method will take for the request
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "/insert")
	@Override
	public Response insert(Person object) {
		System.out.println("Started Insert of: " + object);
		try {
			boolean personOptional = pService.insert(object);
			if (!personOptional) {
				System.out.println("Failed Insert!");
				return Response.status(Response.Status.CONFLICT)
						.entity("Resource invalid or already exists: " + object.toString()).build();
			}
			System.out.println("Inserted!!");
			return Response.status(Response.Status.CREATED).entity(object).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Server wasn't checked for Insert!");
			return Response.status(Response.Status.NOT_FOUND).entity("Could not reach Database").build();
		}

	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "/delete/{id}")
	@Override
	public Response delete(@PathParam("id") Integer id) {
		boolean isPersonDeleted;
		try {
			isPersonDeleted = pService.deleteID(id);
			if (!isPersonDeleted) {
				return Response.status(Response.Status.NOT_FOUND)
						.entity("Resource may not exist. Could not remove resource with ID: " + id).build();
			}
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).entity("Could not reach Database").build();
		}

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(value = "/edit/{id}")
	@Override
	public Response edit(@PathParam("id") Integer id, Person object) {
		System.out.println("Person information sent in: " + object);
		System.out.println("ID Sent in: " + id);
		try {
			boolean personEdited = pService.edit(id, object);
			if (!personEdited) {
				return Response.status(Response.Status.CONFLICT)
						.entity("Resource may not exist. Could not edit resource with ID: " + id).build();
			}
			return Response.status(Response.Status.OK).build();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).entity("Could not reach Database").build();

		}
	}
}