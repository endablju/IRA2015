package pl.edu.uam.restapi.storage.database;

import pl.edu.uam.restapi.storage.model.Game;
import pl.edu.uam.restapi.storage.model.User;

import java.util.Collection;

public interface UserDatabase {
    User getUser(String id);
    User createUser(User user);
    boolean deleteUser(String id);
    User updateUser(String id, User user);
    Collection<User> getUsers();
}
