package pl.edu.uam.restapi.storage.model;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Game")
public class Game {
    private String id;
    private String gameTitle;
    private String genre;
    private String publisher;
    private String developer;

    public Game() {
    }

    public Game(String id, String gameTitle, String genre, String publisher, String developer) {
        this.id = id;
        this.gameTitle = gameTitle;
        this.genre = genre;
        this.publisher = publisher;
        this.developer = developer;
    }

    @ApiModelProperty(value = "Game id", required = true)
    public String getId() {
        return id;
    }

    @ApiModelProperty(value = "Game title", required = true)
    public String getGameTitle() {
        return gameTitle;
    }

    @ApiModelProperty(value = "Game genre", required = true)
    public String getGenre() {
        return genre;
    }

    @ApiModelProperty(value = "Game publisher", required = true)
    public String getPublisher() {
        return publisher;
    }

    @ApiModelProperty(value = "Game developer", required = true)
    public String getDeveloper() {
        return developer;
    }
}
