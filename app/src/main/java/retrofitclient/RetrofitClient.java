package retrofitclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * RetrofitClient is a utility class for creating and configuring Retrofit instances.
 */
public class RetrofitClient {

    private static Dotenv dotenv = Dotenv.configure()
            .directory("/assets")
            .filename("env")
            .load();

    private static final String BASE_URL = "https://api.github.com/";
    private static final String USER = dotenv.get("GITHUB_USER");
    private static final String TOKEN = "Bearer " + dotenv.get("GITHUB_TOKEN");
    private static final String API_VERSION = "2022-11-28";
    private static final String CONTENT_TYPE = "application/vnd.github+json";

    private static Retrofit retrofit = null;

    /**
     * Gets a Retrofit instance with the configured base URL and GsonConverterFactory.
     *
     * @return A Retrofit instance.
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * Gets the authenticated user.
     *
     * @return The authenticated user.
     */
    public static String getUser() {
        return USER;
    }

    /**
     * Gets the authentication token with "Bearer" prefix.
     *
     * @return The authentication token.
     */
    public static String getToken() {
        return TOKEN;
    }

    /**
     * Gets the GitHub API version.
     *
     * @return The GitHub API version.
     */
    public static String getApiVersion() {
        return API_VERSION;
    }

    /**
     * Gets the content type for requests.
     *
     * @return The content type for requests.
     */
    public static String getContentType() {
        return CONTENT_TYPE;
    }
}
