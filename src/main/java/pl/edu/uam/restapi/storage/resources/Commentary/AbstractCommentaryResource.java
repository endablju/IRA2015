package pl.edu.uam.restapi.storage.resources.Commentary;

import com.wordnik.swagger.annotations.ApiOperation;
import pl.edu.uam.restapi.documentation.exceptions.CommentaryException;
import pl.edu.uam.restapi.storage.database.CommentaryDatabase;
import pl.edu.uam.restapi.storage.entity.CommentaryEntity;
import pl.edu.uam.restapi.storage.model.Commentary;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;

/**
 * Created by MightySheldor on 2015-06-08.
 */
public abstract class AbstractCommentaryResource {

    protected abstract CommentaryDatabase getDatabase();

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get commentary collection", notes = "Get commentary collection", response = Commentary.class, responseContainer = "LIST")
    public Collection<Commentary> list() {
        return getDatabase().getCommentary();
    }


    @Path("/{commentsId}")
    @ApiOperation(value = "Get comments by id", notes = "[note]Get comments by id", response = Commentary.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Commentary getCommentary(@PathParam("commentsId") String commmentaryId) throws Exception {
        Commentary commentary = getDatabase().getCommentary(commmentaryId);

        if (commmentaryId.equals("db")) {
            throw new Exception("Database error");
        }

        if (commentary == null) {
            throw new CommentaryException("Commentary not found", "Komentarz nie został znaleziony", "http://docu.pl/errors/game-not-found");
        }

        return commentary;
    }

    @POST
    @ApiOperation(value = "Create commentary", notes = "Create commentary", response = Commentary.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCommentary(Commentary commentary) {
        Commentary dbCommentary = new Commentary(
                "",
                commentary.getCommentary(),
                commentary.getGameTitle(),
                commentary.getUserName()
        );

        Commentary createdCommentary = getDatabase().createCommentary(dbCommentary);

        return Response.created(URI.create(uriInfo.getPath() + "/" + createdCommentary.getId())).entity(createdCommentary).build();
    }

    @DELETE
    @ApiOperation(value = "Delete commentarty", notes = "Delete commentarty", response = CommentaryEntity.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{commentsId}")
    public Response deleteCommentary(@PathParam("commentsId") String commentaryId) {

        boolean isDeleted = getDatabase().deleteCommentary(commentaryId);

        if (isDeleted)
            return Response.ok().build();
        else
            throw new CommentaryException("Commentary not found", "Komentarz nie został znaleziony", "http://docu.pl/errors/game-not-found");

    }

    @PUT
    @Path("/{commentsId}")
    @ApiOperation(value = "Update commentary", notes = "Update commentary", response = CommentaryEntity.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCommentary(@PathParam("commentsId") String commentaryId, Commentary commentary)
    {

        Commentary updatedCommentary = getDatabase().updateCommentary(commentaryId, commentary);

        return Response.created(URI.create(uriInfo.getPath() + "/" + updatedCommentary.getId())).entity(updatedCommentary).build();
    }
}
