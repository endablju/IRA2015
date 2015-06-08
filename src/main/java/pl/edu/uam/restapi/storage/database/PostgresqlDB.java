package pl.edu.uam.restapi.storage.database;

import com.google.common.collect.Lists;
import pl.edu.uam.restapi.storage.entity.CommentaryEntity;
import pl.edu.uam.restapi.storage.entity.GameEntity;
import pl.edu.uam.restapi.storage.entity.UserEntity;
import pl.edu.uam.restapi.storage.model.Commentary;
import pl.edu.uam.restapi.storage.model.Game;
import pl.edu.uam.restapi.storage.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostgresqlDB implements UserDatabase, GameDatabase, CommentaryDatabase{

    private static final String HOST = "babar.elephantsql.com";
    private static final int PORT = 5432;
    private static final String DATABASE = "lktodnsz";
    private static final String USER_NAME = "lktodnsz";
    private static final String PASSWORD = "ZovzcqAkgpBzSYV-M7Ndw6gr76GKeOnA";

    private static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            String dbUrl = "jdbc:postgresql://" + HOST + ':' + PORT + "/" + DATABASE;

            Map<String, String> properties = new HashMap<String, String>();

            properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
            properties.put("hibernate.connection.url", dbUrl);
            properties.put("hibernate.connection.username", USER_NAME);
            properties.put("hibernate.connection.password", PASSWORD);
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.format_sql", "true");

            properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false"); //PERFORMANCE TIP!
            properties.put("hibernate.hbm2ddl.auto", "update"); //update schema for entities (create tables if not exists)

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myUnit", properties);
            entityManager = emf.createEntityManager();
        }

        return entityManager;
    }

    // --------------------- USER --------------------------
    @Override
    public User getUser(String sid) {
        Long id = null;

        try {
            id = Long.valueOf(sid);
        } catch (NumberFormatException e) {
            return null;
        }

        UserEntity userEntity = getEntityManager()
                .find(UserEntity.class, id);

        if (userEntity != null) {
            return buildUserResponse(userEntity);
        }

        return null;
    }

    @Override
    public User createUser(final User user) {
        UserEntity entity = buildUserEntity(user, false);

        try {
            getEntityManager().getTransaction().begin();

            // Operations that modify the database should come here.
            getEntityManager().persist(entity);

            getEntityManager().getTransaction().commit();
        } finally {
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }
        }

        return new User(String.valueOf(entity.getId()), entity.getFirstName(), entity.getLastName());
    }

    @Override
    public boolean deleteUser(String sid) {
        Long id = null;

        try {
            id = Long.valueOf(sid);
        } catch (NumberFormatException e) {
            return false;
        }

        UserEntity entity = getEntityManager().find(UserEntity.class, id);

        if (entity != null) {
            getEntityManager().getTransaction().begin();
            getEntityManager().remove(entity);
            getEntityManager().getTransaction().commit();
            return true;
        }

        return false;
    }

    @Override
    public User updateUser(String sid, User user) {
        Long id = null;

        try {
            id = Long.valueOf(sid);
        } catch (NumberFormatException e) {
            return null;
        }

        UserEntity entity = getEntityManager().find(UserEntity.class, id);
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());

        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();

        return buildUserResponse(entity);
    }

    @Override
    public Collection<User> getUsers() {
        Query query = getEntityManager().createNamedQuery("users.findAll");
        List<UserEntity> resultList = query.getResultList();

        List<User> list = Collections.emptyList();

        if (resultList != null && !resultList.isEmpty()) {
            list = Lists.newArrayListWithCapacity(resultList.size());

            for (UserEntity user : resultList) {
                list.add(buildUserResponse(user));
            }
        }

        return list;
    }

    private User buildUserResponse(UserEntity userEntity) {
        return new User(userEntity.getId().toString(), userEntity.getFirstName(), userEntity.getLastName());
    }

    private UserEntity buildUserEntity(User user, boolean active) {
        return new UserEntity(user.getFirstName(), user.getLastName(), active);
    }

    // --------------------- GAME --------------------------
    @Override
    public Game getGame(String sid)
    {
        Long id = null;

        try
        {
            id = Long.valueOf(sid);
        }
        catch (NumberFormatException e)
        {
            return null;
        }

        GameEntity gameEntity = getEntityManager().find(GameEntity.class, id);

        if (gameEntity != null) {
            return buildGameResponse(gameEntity);
        }

        return null;
    }

    @Override
    public Game createGame(final Game game) {
        GameEntity entity = buildGameEntity(game, false);

        try {
            getEntityManager().getTransaction().begin();

            // Operations that modify the database should come here.
            getEntityManager().persist(entity);

            getEntityManager().getTransaction().commit();
        } finally {
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }
        }

        return new Game(String.valueOf(entity.getId()), entity.getGameTitle(), entity.getGenre(), entity.getPublisher(), entity.getDeveloper());
    }

    @Override
    public boolean deleteGame(String sid) {
        Long id = null;

        try {
            id = Long.valueOf(sid);
        } catch (NumberFormatException e) {
            return false;
        }

        GameEntity entity = getEntityManager().find(GameEntity.class, id);

        if (entity != null) {
            getEntityManager().getTransaction().begin();
            getEntityManager().remove(entity);
            getEntityManager().getTransaction().commit();
            return true;
        }

        return false;
    }

    @Override
    public Game updateGame(String sid, Game game) {
        Long id = null;

        try {
            id = Long.valueOf(sid);
        } catch (NumberFormatException e) {
            return null;
        }

        GameEntity entity = getEntityManager().find(GameEntity.class, id);
        entity.setGameTitle(game.getGameTitle());
        entity.setGenre(game.getGenre());
        entity.setPublisher(game.getPublisher());
        entity.setDeveloper(game.getDeveloper());

        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();

        return buildGameResponse(entity);
    }

    @Override
    public Collection<Game> getGames() {
        Query query = getEntityManager().createNamedQuery("games.findAll");
        List<GameEntity> resultList = query.getResultList();

        List<Game> list = Collections.emptyList();

        if (resultList != null && !resultList.isEmpty()) {
            list = Lists.newArrayListWithCapacity(resultList.size());

            for (GameEntity game : resultList) {
                list.add(buildGameResponse(game));
            }
        }

        return list;
    }

    private Game buildGameResponse(GameEntity gameEntity) {
        return new Game(gameEntity.getId().toString(), gameEntity.getGameTitle(), gameEntity.getGenre(), gameEntity.getPublisher(), gameEntity.getDeveloper());
    }

    private GameEntity buildGameEntity(Game game, boolean active) {
        return new GameEntity(game.getGameTitle(), game.getGenre(), game.getPublisher(), game.getDeveloper(), active);
    }

    // --------------------- COMMENTARY --------------------------

    @Override
    public Commentary getCommentary(String sid) {
        Long id = null;

        try
        {
            id = Long.valueOf(sid);
        }
        catch (NumberFormatException e)
        {
            return null;
        }

        CommentaryEntity commentaryEntity = getEntityManager().find(CommentaryEntity.class, id);

        if (commentaryEntity != null) {
            return buildCommentaryResponse(commentaryEntity);
        }

        return null;
    }

    @Override
    public Commentary createCommentary(Commentary commentary) {
        CommentaryEntity entity = buildCommentaryEntity(commentary, false);

        try {
            getEntityManager().getTransaction().begin();

            // Operations that modify the database should come here.
            getEntityManager().persist(entity);

            getEntityManager().getTransaction().commit();
        } finally {
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }
        }

        return new Commentary(String.valueOf(entity.getId()), entity.getCommentary(), entity.getGameTitle(), entity.getUserName());
    }

    @Override
    public boolean deleteCommentary(String sid) {
        Long id = null;

        try {
            id = Long.valueOf(sid);
        } catch (NumberFormatException e) {
            return false;
        }

        CommentaryEntity entity = getEntityManager().find(CommentaryEntity.class, id);

        if (entity != null) {
            getEntityManager().getTransaction().begin();
            getEntityManager().remove(entity);
            getEntityManager().getTransaction().commit();
            return true;
        }

        return false;
    }

    @Override
    public Commentary updateCommentary(String sid, Commentary commentary) {
        Long id = null;

        try {
            id = Long.valueOf(sid);
        } catch (NumberFormatException e) {
            return null;
        }

        CommentaryEntity entity = getEntityManager().find(CommentaryEntity.class, id);
        entity.setCommentary(commentary.getCommentary());
        entity.setGameTitle(commentary.getGameTitle());
        entity.setUserName(commentary.getUserName());

        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();

        return buildCommentaryResponse(entity);
    }

    @Override
    public Collection<Commentary> getCommentary() {
        Query query = getEntityManager().createNamedQuery("comments.findAll");
        List<CommentaryEntity> resultList = query.getResultList();

        List<Commentary> list = Collections.emptyList();

        if (resultList != null && !resultList.isEmpty()) {
            list = Lists.newArrayListWithCapacity(resultList.size());

            for (CommentaryEntity commentary : resultList) {
                list.add(buildCommentaryResponse(commentary));
            }
        }

        return list;
    }

    private Commentary buildCommentaryResponse(CommentaryEntity commentaryEntity) {
        return new Commentary(commentaryEntity.getId().toString(), commentaryEntity.getCommentary(), commentaryEntity.getGameTitle(), commentaryEntity.getUserName());
    }

    private CommentaryEntity buildCommentaryEntity(Commentary commentary, boolean active) {
        return new CommentaryEntity(commentary.getCommentary(), commentary.getGameTitle(), commentary.getUserName(), active);
    }
}
