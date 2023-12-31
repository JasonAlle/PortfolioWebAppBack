package rest;

import java.sql.SQLException;

import entity.Person;
import entity.Tax;
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
import services.TaxService;

@Path("/tax")
public class TaxRest implements RestCode<Tax> {

	@Inject
	TaxService tService;
	@Inject
	PersonService pService;

	@GET // Responds to a get request from http
	@Produces(MediaType.APPLICATION_JSON) // Defines what this method will produce for the request
	@Path(value = "/{id}") // The path of the request
	@Override
	public Response find(@PathParam("id") Integer id) {
		System.out.println("Started Find: " + id);
		try {
			Tax tax = tService.find(id);
			if (tax == null) {
				System.out.println(id + " Was not found!");

				return Response.status(Response.Status.NOT_FOUND)
						.entity("The id is invalid or the resource could not be found: " + id).build();
			}
			System.out.println("Found: " + id);
			System.out.println(tax.toString());
			return Response.status(Response.Status.OK).entity(tax).build();
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
			return Response.status(Response.Status.OK).entity(tService.findAll()).build();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).entity("Could not reach Database").build();
		}

	}
	
	@GET // Responds to a get request from http
	@Produces(MediaType.APPLICATION_JSON) // Defines what this method will produce for the request
	@Path(value = "/findAllTaxesByPersonId/{id}") // The path of the request
	public Response findAllTaxesByPersonId(@PathParam("id") Integer id) {
		System.out.println("Started Find All!");
		try {
			return Response.status(Response.Status.OK).entity(tService.findAllTaxesByPersonId(id)).build();

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
	public Response insert(Tax object) {
		System.out.println("Started Insert of: " + object);
		System.out.println("Person sent: " + object.getPerson());
		try {
			Person personToAttach = pService.find(object.getPerson().getId());
			
			if (personToAttach == null)
			{
				System.out.println("Could not find person to attach to: " + personToAttach);
				return Response.status(Response.Status.NOT_FOUND).entity("Could not find Person to attach tax to!").build();
			}
			object.setPerson(personToAttach);
			boolean TaxOptional = tService.insert(object);
			if (!TaxOptional) {
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
		boolean isTaxDeleted;
		try {
			isTaxDeleted = tService.deleteID(id);
			if (!isTaxDeleted) {
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
	public Response edit(@PathParam("id") Integer id, Tax object) {
		try {
			boolean taxEdited = tService.edit(id, object);
			if (!taxEdited) {
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
