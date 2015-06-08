package pl.edu.uam.restapi.storage.resources.Games;

import com.wordnik.swagger.annotations.Api;
import pl.edu.uam.restapi.storage.database.GameDatabase;
import pl.edu.uam.restapi.storage.database.PostgresqlDB;

import javax.ws.rs.Path;

@Path("/games")
@Api(value = "/games", description = "Operations about games")
public class PostgresGamesResource extends AbstractGamesResource {

    private static final GameDatabase database = new PostgresqlDB();

    @Override
    protected GameDatabase getDatabase() {
        return database;
    }
}
