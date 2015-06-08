package pl.edu.uam.restapi.storage.resources.Commentary;

import com.wordnik.swagger.annotations.Api;
import pl.edu.uam.restapi.storage.database.CommentaryDatabase;
import pl.edu.uam.restapi.storage.database.PostgresqlDB;

import javax.ws.rs.Path;

@Path("/comments")
@Api(value = "/comments", description = "Operations about comments")
public class PostgresCommentaryResource extends AbstractCommentaryResource {

    private static final CommentaryDatabase database = new PostgresqlDB();

    @Override
    protected CommentaryDatabase getDatabase() {
        return database;
    }
}
