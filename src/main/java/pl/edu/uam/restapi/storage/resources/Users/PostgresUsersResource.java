package pl.edu.uam.restapi.storage.resources.Users;

import com.wordnik.swagger.annotations.Api;
import pl.edu.uam.restapi.storage.database.PostgresqlDB;
import pl.edu.uam.restapi.storage.database.UserDatabase;
import pl.edu.uam.restapi.storage.resources.Users.AbstractUsersResource;

import javax.ws.rs.Path;

@Path("/users")
@Api(value = "/users", description = "Operations about users")
public class PostgresUsersResource extends AbstractUsersResource {

    private static final UserDatabase database = new PostgresqlDB();

    @Override
    protected UserDatabase getDatabase() {
        return database;
    }

}
