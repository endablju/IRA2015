package pl.edu.uam.restapi.storage.database;

import pl.edu.uam.restapi.storage.model.Game;

import java.util.Collection;

/**
 * Created by MightySheldor on 2015-06-08.
 */
public interface GameDatabase {
    Game getGame(String id);
    Game createGame(Game game);
    boolean deleteGame(String id);
    Game updateGame(String id, Game game);
    Collection<Game> getGames();
}