package pl.edu.uam.restapi.storage.model;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Commentary")
public class Commentary {
    private String id;
    private String commentary;
    private String gameTitle;
    private String userName;

    public Commentary() {
    }

    public Commentary(String id, String commentary, String gameTitle, String userName) {
        this.id = id;
        this.commentary = commentary;
        this.gameTitle = gameTitle;
        this.userName = userName;
    }

    @ApiModelProperty(value = "Comment id", required = true)
    public String getId() {
        return id;
    }

    @ApiModelProperty(value = "Message (comment)", required = true)
    public String getCommentary() {
        return commentary;
    }

    @ApiModelProperty(value = "Game genre", required = true)
    public String getGameTitle() {
        return gameTitle;
    }

    @ApiModelProperty(value = "Game publisher", required = true)
    public String getUserName() {
        return userName;
    }

}
