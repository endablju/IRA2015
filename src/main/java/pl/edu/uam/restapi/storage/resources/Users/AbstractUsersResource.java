package pl.edu.uam.restapi.storage.resources.Users;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponses;
import pl.edu.uam.restapi.storage.database.UserDatabase;
import pl.edu.uam.restapi.storage.entity.UserEntity;
import pl.edu.uam.restapi.storage.model.User;
import pl.edu.uam.restapi.documentation.exceptions.UserException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;

public abstract class AbstractUsersResource {

    protected abstract UserDatabase getDatabase();

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get users collection", notes = "Get users collection", response = User.class, responseContainer = "LIST")
    public Collection<User> list() {
        return getDatabase().getUsers();
    }

    @Path("/{userId}")
    @ApiOperation(value = "Get user by id", notes = "[note]Get user by id", response = User.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("userId") String userId) throws Exception {
        User user = getDatabase().getUser(userId);

        if (userId.equals("db")) {
            throw new Exception("Database error");
        }

        if (user == null) {
            throw new UserException("User not found", "Użytkownik nie został znaleziony", "http://docu.pl/errors/user-not-found");
        }

        return user;
    }

    @POST
    @ApiOperation(value = "Create user", notes = "Create user", response = User.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        User dbUser = new User(
                "",
                user.getFirstName(),
                user.getLastName()
        );

        User createdUser = getDatabase().createUser(dbUser);

        return Response.created(URI.create(uriInfo.getPath() + "/" + createdUser.getId())).entity(createdUser).build();
    }

    @DELETE
    @ApiOperation(value = "Delete user", notes = "Delete user", response = UserEntity.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}")
    public Response deleteUser(@PathParam("userId") String userId) {

        boolean isDeleted = getDatabase().deleteUser(userId);

        if (isDeleted)
            return Response.ok().build();
        else
            throw new UserException("User not found", "Użytkownik nie został znaleziony", "http://docu.pl/errors/user-not-found");

    }

    @PUT
    @Path("/{userId}")
    @ApiOperation(value = "Update user", notes = "Update user", response = UserEntity.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("userId") String userId, User user)
    {

        User updatedUser = getDatabase().updateUser(userId, user);

        return Response.created(URI.create(uriInfo.getPath() + "/" + updatedUser.getId())).entity(updatedUser).build();
    }
}
