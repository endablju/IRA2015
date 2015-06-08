package pl.edu.uam.restapi.storage.database;

import pl.edu.uam.restapi.storage.model.Commentary;

import java.util.Collection;

public interface CommentaryDatabase {
    Commentary getCommentary(String id);
    Commentary createCommentary(Commentary commentary);
    boolean deleteCommentary(String id);
    Commentary updateCommentary(String id, Commentary commentary);
    Collection<Commentary> getCommentary();
}