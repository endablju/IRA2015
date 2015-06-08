package pl.edu.uam.restapi.storage.resources.Games;

import com.wordnik.swagger.annotations.ApiOperation;
import pl.edu.uam.restapi.documentation.exceptions.GameException;
import pl.edu.uam.restapi.storage.database.GameDatabase;
import pl.edu.uam.restapi.storage.entity.GameEntity;
import pl.edu.uam.restapi.storage.model.Game;

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
public abstract class AbstractGamesResource {

    protected abstract GameDatabase getDatabase();

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get games collection", notes = "Get games collection", response = Game.class, responseContainer = "LIST")
    public Collection<Game> list() {
        return getDatabase().getGames();
    }


    @Path("/{gameId}")
    @ApiOperation(value = "Get game by id", notes = "[note]Get game by id", response = Game.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Game getGame(@PathParam("gameId") String gameId) throws Exception {
        Game game = getDatabase().getGame(gameId);

        if (gameId.equals("db")) {
            throw new Exception("Database error");
        }

        if (game == null) {
            throw new GameException("Game not found", "Gra nie została znaleziona", "http://docu.pl/errors/game-not-found");
        }

        return game;
    }

    @POST
    @ApiOperation(value = "Create game", notes = "Create game", response = Game.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGame(Game game) {
        Game dbGame = new Game(
                "",
                game.getGameTitle(),
                game.getGenre(),
                game.getPublisher(),
                game.getDeveloper()
        );

        Game createdGame = getDatabase().createGame(dbGame);

        return Response.created(URI.create(uriInfo.getPath() + "/" + createdGame.getId())).entity(createdGame).build();
    }

    @DELETE
    @ApiOperation(value = "Delete game", notes = "Delete game", response = GameEntity.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{gameId}")
    public Response deleteGame(@PathParam("gameId") String gameId) {

        boolean isDeleted = getDatabase().deleteGame(gameId);

        if (isDeleted)
            return Response.ok().build();
        else
            throw new GameException("Game not found", "Gra nie została znaleziona", "http://docu.pl/errors/user-not-found");

    }

    @PUT
    @Path("/{gameId}")
    @ApiOperation(value = "Update game", notes = "Update game", response = GameEntity.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGame(@PathParam("gameId") String gameId, Game game)
    {
        Game updatedGame = getDatabase().updateGame(gameId, game);

        return Response.created(URI.create(uriInfo.getPath() + "/" + updatedGame.getId())).entity(updatedGame).build();
    }
}
