package pl.edu.uam.restapi.storage.entity;

import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@NamedQueries({
        @NamedQuery(name = "comments.findAll", query = "SELECT c FROM CommentaryEntity c")
})
public class CommentaryEntity {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentaryEntity.class);

    // auto-generated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //fields can be renamed
    @Column(name = "commentary")
    private String commentary;

    //fields can be renamed
    @Column(name = "gameTitle")
    private String gameTitle;

    //fields can be renamed
    @Column(name = "userName")
    private String userName;

    //fields can be indexed for better performance
    private boolean active = false;

    //Lifecycle methods -- Pre/PostLoad, Pre/PostPersist...
    @PostLoad
    private void postLoad() {
        LOGGER.info("postLoad: {}", toString());
    }

    public CommentaryEntity() {
    }

    public CommentaryEntity(String commentary, String gameTitle, String userName, boolean active) {
        this.commentary = commentary;
        this.gameTitle = gameTitle;
        this.userName = userName;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("commentary", commentary)
                .add("gameTitle", gameTitle)
                .add("userName", userName)
                .add("active", active)
                .toString();
    }
}
