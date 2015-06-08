package pl.edu.uam.restapi.storage.entity;

import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name = "games")
@NamedQueries({
        @NamedQuery(name = "games.findAll", query = "SELECT g FROM GameEntity g")
})
public class GameEntity {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameEntity.class);

    // auto-generated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //fields can be renamed
    @Column(name = "title")
    private String gameTitle;

    //fields can be renamed
    @Column(name = "genre")
    private String genre;

    //fields can be renamed
    @Column(name = "publisher")
    private String publisher;

    //fields can be renamed
    @Column(name = "developer")
    private String developer;

    //fields can be indexed for better performance
    private boolean active = false;

    //Lifecycle methods -- Pre/PostLoad, Pre/PostPersist...
    @PostLoad
    private void postLoad() {
        LOGGER.info("postLoad: {}", toString());
    }

    public GameEntity() {
    }

    public GameEntity(String gameTitle, String genre, String publisher, String developer, boolean active) {
        this.gameTitle = gameTitle;
        this.genre = genre;
        this.publisher = publisher;
        this.developer = developer;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("gameTitle", gameTitle)
                .add("genre", genre)
                .add("publisher", publisher)
                .add("developer", developer)
                .add("active", active)
                .toString();
    }
}
