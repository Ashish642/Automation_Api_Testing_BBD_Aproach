package com.spotify;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SpotifyApi {
    public String token;
    public  String trackId= "0sSapNABKrGwgaMhn0p8uL";
//    public String userId= "31ksdb253w3qxomtatkeoj7kvbfm";
//    public String playlistId= "2o5I9wP7FH36ebPt6mPjRf";
    public String userId= "";
    public String playlistId= "";





    @BeforeTest
    public void getToken() {
         token="Bearer BQA4ZM7_77GNtdFlzz6xEEFzL3jw3pz1pYx37kNwpy599MUrFw7MSllcqRMxqtfGLCKkRRsMoslFQjZCsGQv-2SABMu4ITMObR4eHbVAZNOq8C2ueJPab8NttNBHUPVGPtA7thFqs21vt3Z9kGcTxhIepDPUbSNegVvXj7mmCZe1w4o301Azi2GPZMV7hwkSFbcBuRGhfoiWvthi8Hu5p7MubhMcC9J5XhuwKpzESzyn7o6uwcbi8SLGhlW9SCAjye11FcY1sW_aeMG5fxkcOz4xfEdndQMzD0ebZVUj5hWy90NSzL8tIXpW-p30LU3IyzWkd3hX_Fj7";



    }

     @Test (priority = 1)
    public void testGet_CurrentUserProfile() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/me");
        response.prettyPrint();
//        int StatusCode = response.getStatusCode();
//        System.out.println("StatusCode" +StatusCode);
        response.then().assertThat().statusCode(200);
         userId = response.path("id");
         System.out.println("user id is:"+userId);
         Assert.assertEquals(userId,"31ksdb253w3qxomtatkeoj7kvbfm");
    }
    @Test (priority = 2)
    public void testGet_UserProfile() {
       Response response = RestAssured.given().contentType(ContentType.JSON)
               .accept(ContentType.JSON)
               .header("Authorization", token)
               .when()
               .get ("https://api.spotify.com/v1/users/"+userId+"");
          response.prettyPrint();
        userId=response.path("Id");
       System.out.println("userID:"+userId);
//       int StatusCode = response.getStatusCode();
//        System.out.println("StatusCode" +StatusCode);
        response.then().assertThat().statusCode(200);
    }
    @Test (priority = 3)
    public void createPlaylist() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        "  \"name\": \"Spotify automation playlist\",\n" +
                        "  \"description\": \"New playlist description\",\n" +
                        "  \"public\": false\n" +
                        "}")
//                .post ("https://api.spotify.com/v1/users/31ksdb253w3qxomtatkeoj7kvbfm/playlists");
        .post("https://api.spotify.com/v1/users/"+userId+"/playlists");
        response.prettyPrint();
        playlistId = response.path("Id");
        System.out.println("playlistID:"+playlistId);
        response.then().assertThat().statusCode(201);


    }
    @Test (priority = 4)
    public void searchForItem() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .queryParam("q","Arijit singh")
                .queryParam("type","track")
                .when()
                .get("https://api.spotify.com/v1/search");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }
    @Test (priority = 5)
    public void AddItemInPlaylist() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .queryParam("uris","spotify:track:46NYX9zIml71qtfYYjakTI,spotify:track:5WzfGg2ueNoOS5aIkaR9qX,spotify:track:5Ox43gIWUNW6pAgx3F3oi7")
                .post("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(201);

    }
    @Test (priority = 6)
    public void UpdatePlaylistItem() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        "  \"range_start\": 1,\n" +
                        "  \"insert_before\": 0,\n" +
                        "  \"range_length\": 2\n" +
                        "}")
                .put("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    @Test (priority = 7)

    public void ChangePlaylistDetails() {

        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        "  \"name\": \"Spotify4 automation playlist\",\n" +
                        "  \"description\": \"Updated playlist description\",\n" +
                        "  \"public\": false\n" +
                        "}")
                .put("https://api.spotify.com/v1/playlists/"+playlistId);

        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }
    @Test (priority = 8)

    public void GetUserCurrentPlaylist() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/me/playlists");
        response.prettyPrint();
        playlistId=response.path("Id");
        System.out.println("userID:"+playlistId);
        int StatusCode = response.getStatusCode();
        System.out.println("StatusCode" +StatusCode);
        response.then().assertThat().statusCode(200);


    }
    @Test (priority = 9)

    public void GetPlaylistItem() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
//                .get ("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
        .get("https://api.spotify.com/v1/playlists/2o5I9wP7FH36ebPt6mPjRf/tracks");
        response.prettyPrint();
        playlistId=response.path("Id");
        System.out.println("userID:"+playlistId);
        response.then().assertThat().statusCode(200);


    }
    @Test (priority = 10)

    public void GetPlaylistCoverImage() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
//                .get ("https://api.spotify.com/v1/playlists/"+playlistId+"/images");
        .get("https://api.spotify.com/v1/playlists/2o5I9wP7FH36ebPt6mPjRf/images");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

    }
    @Test (priority = 11)

    public void GetPlayList() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
//                .get ("https://api.spotify.com/v1/playlists/"+playlistId+"");
        .get("https://api.spotify.com/v1/playlists/2o5I9wP7FH36ebPt6mPjRf");
        response.prettyPrint();
        playlistId=response.path("Id");
        System.out.println("userID:"+playlistId);
        int StatusCode = response.getStatusCode();
        System.out.println("StatusCode" +StatusCode);
        response.then().assertThat().statusCode(200);
    }
    @Test (priority = 12)

    public void RemovePlaylistItem() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .body("{ \"tracks\": [{ \"uri\": \"spotify:track:46NYX9zIml71qtfYYjakTI\" }] }")
//                .delete ("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
        .delete("https://api.spotify.com/v1/playlists/2o5I9wP7FH36ebPt6mPjRf/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

    }
    @Test (priority = 13)

    public void GetTrackAudioAnalysis() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/audio-analysis/5WzfGg2ueNoOS5aIkaR9qX");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

    }
    @Test (priority = 14)

    public void GetTracksAudioFeatures() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/audio-features");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

    }
    @Test (priority = 15)
    public void GetSeveralTracks() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .param("ids",trackId)
                .when()
                .get ("https://api.spotify.com/v1/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

    }
    @Test (priority = 16)

    public void GetTrack() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/tracks/5Ox43gIWUNW6pAgx3F3oi7");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }
    @Test(priority = 17)

    public void GetTrackAudioFeatures() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/audio-features");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

    }

}
