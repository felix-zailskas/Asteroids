package aoop.asteroids.util;

import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;

public class HighScoreEvents {

    public static JSONArray getHighScores() {
        return Unirest.get("https://asteroids-database.herokuapp.com/scores")
                .header("content-type", "application/json")
                .asJson().getBody().getArray();
    }
}
