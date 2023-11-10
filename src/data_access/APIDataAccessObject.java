package data_access;

// libraries for working with APIs
import entity.Song;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class APIDataAccessObject {

    // load CLIENT_ID and CLIENT_SECRET from env variable.
    private static final String CLIENT_ID = System.getenv("CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");

    public static String requestAccessToken() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", CLIENT_ID)
                .add("client_secret", CLIENT_SECRET)
                .build();

        Request request = new Request.Builder()
                .url("https://accounts.spotify.com/api/token")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(formBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            JSONObject responseBody = new JSONObject(response.body().string());

            return responseBody.getString("access_token");
        }
        catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject requestPlaylistData(String accessToken, String playlistId) {
        // In this case, we are using playlistId = 37i9dQZF1DX5Ejj0EkURtP which refers to the "All Out 2010s" playlist

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/playlists/".concat(playlistId).concat("/tracks"))
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        }
        catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // todo: method to return a Song object
}
