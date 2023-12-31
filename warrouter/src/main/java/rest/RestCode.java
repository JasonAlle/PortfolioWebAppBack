package rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public interface RestCode<T> {

	@GET // Responds to a get request from http
	@Produces(MediaType.APPLICATION_JSON) // Defines what this method will produce for the request
	@Path(value = "/{id}") // The path of the request
	public Response find(Integer id);

	@GET // Responds to a get request from http
	@Produces(MediaType.APPLICATION_JSON) // Defines what this method will produce for the request
	@Path(value = "/findAll") // The path of the request
	public Response findAll();

	@POST
	@Consumes(MediaType.APPLICATION_JSON) // Defines what this method will take for the request
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "/insert")
	public Response insert(T object);

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "/delete/{id}")
	public Response delete(Integer id);

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(value = "/edit/{id}")
	public Response edit(Integer id, T object);

}
