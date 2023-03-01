package com.example.revision27.models;

import org.bson.Document;
import org.bson.types.ObjectId;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    
    private ObjectId _id;
    private Integer gId;
    private String name;
    private Integer year;
    private Integer ranking;
    private Integer usersRated;
    private String url;
    private String image;

    public JsonObject toJsonObject() {

        return Json.createObjectBuilder()
            .add("game_id", this.getGId())
            .add("name", this.getName())
            .build();
    }

    public static Game create(Document doc) {
        Game game = new Game();
        if (doc == null) {
            return null;
        }

        game.set_id(doc.getObjectId("_id"));
        game.setGId(doc.getInteger("gid"));
        game.setName(doc.getString("name"));
        game.setYear(doc.getInteger("year"));
        game.setRanking(doc.getInteger("ranking"));
        game.setUsersRated(doc.getInteger("users_rated"));
        game.setUrl(doc.getString("url"));
        game.setImage(doc.getString("image"));

        return game;
    }

}
