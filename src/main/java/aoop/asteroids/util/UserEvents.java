package aoop.asteroids.util;

import kong.unirest.JsonNode;

public interface UserEvents {

     void setUpUser(String name);

     void setUserInfo(String name, int id);

     JsonNode getRequestName(String name);

     int postRequestName(String name);

     void postRequestScore();

}