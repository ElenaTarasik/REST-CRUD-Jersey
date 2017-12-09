package com.simple.rest.controller;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import com.simple.rest.dao.UserDao;
import com.simple.rest.model.User;

@Path("/app")
public class MyResource {

	private static UserDao userDao = new UserDao();

	@GET
	@Path("/user/{id}")
	@Produces("application/json")
	public User getUser(@PathParam("id") long id) {
		try {
			return userDao.getUser(id);
		} catch (Exception e) {
			System.out.println("User didn't find");
			return null;
		}
	}

	@GET
	@Path("/users")
	@Produces("application/json")
	public List<User> getAllUsers() {
		List<User> users = userDao.getUsers();
		return users;
	}

	@POST
	@Path("/create")
	@Consumes("application/json")
	public Response addUser(User user) {
		user.setName(user.getName());
		user.setAge(user.getAge());
		user.setGender(user.getGender());
		try {
			userDao.addUser(user);
		} catch (Exception e) {
			System.out.println("User didn't create");
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.ok().build();
	}

	@PUT
	@Path("/update/{id}")
	@Consumes("application/json")
	public Response updateUser(@PathParam("id") long id, User user) {
		try {
			userDao.updateUser(id, user);
		} catch (Exception e) {
			System.out.println("Such user doesn't exist, so can not be updated");
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.ok().build();
	}

	@DELETE
	@Path("/delete/{id}")
	@Consumes("application/json")
	public Response deleteUser(@PathParam("id") long id) {
		try {
			userDao.deleteUser(id);
		} catch (Exception e) {
			System.out.println("User didn't delete");
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.ok().build();
	}
}
