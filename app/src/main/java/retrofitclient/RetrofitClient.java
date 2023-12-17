package retrofitclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import io.github.cdimascio.dotenv.Dotenv;

public class RetrofitClient {
    private static Dotenv dotenv = Dotenv.configure()
            .directory("/assets")
            .filename("env")
            .load();

    private static final String BASE_URL = "https://api.github.com/";
    private static final String USER = dotenv.get("GITHUB_USER");
    private static final String TOKEN = "Bearer " + dotenv.get("GITHUB_TOKEN");
    private static final String APIVERSION = "2022-11-28";
    private static final String CONTENT = "application/vnd.github+json";

    private static Retrofit retrofit = null;

    /**
     * Funcion de metodo para singleton de cliente api
     * @return
     */
    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }
    public static String getUser(){
        return USER;
    }
    public static String getToken(){
        return TOKEN;
    }

    public static String getApiVersion() {
        return APIVERSION;
    }

    public static String getContentType() {
        return CONTENT;
    }
}
