package retrofitclient;

import java.util.List;

import models.GitHubRepo;
import models.GitHubUser;
import models.RepoPost;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * GithubApiClient is an interface defining Retrofit API endpoints for GitHub interactions.
 */
public interface GithubApiClient {

    /**
     * Retrieves information about the authenticated user.
     *
     * @param contentType The content type of the request.
     * @param authorization The authorization token for the request.
     * @param apiVersion The GitHub API version.
     * @return A Call object representing the asynchronous execution of the request.
     */
    @GET("/user")
    Call<GitHubUser> getUser(
            @Header("accept") String contentType,
            @Header("authorization") String authorization,
            @Header("X-GitHub-Api-Version") String apiVersion
    );

    /**
     * Retrieves a list of repositories for the authenticated user.
     *
     * @param contentType The content type of the request.
     * @param authorization The authorization token for the request.
     * @param apiVersion The GitHub API version.
     * @param limit The number of repositories to retrieve per page.
     * @param order The sort order for the repositories.
     * @return A Call object representing the asynchronous execution of the request.
     */
    @GET("/user/repos")
    Call<List<GitHubRepo>> getRepos(
            @Header("accept") String contentType,
            @Header("authorization") String authorization,
            @Header("X-GitHub-Api-Version") String apiVersion,
            @Query("per_page") int limit,
            @Query("sort") String order
    );

    /**
     * Creates a new repository for the authenticated user.
     *
     * @param contentType The content type of the request.
     * @param authorization The authorization token for the request.
     * @param apiVersion The GitHub API version.
     * @param repo The repository data to be created.
     * @return A Call object representing the asynchronous execution of the request.
     */
    @POST("/user/repos")
    Call<GitHubRepo> createRepo(
            @Header("accept") String contentType,
            @Header("authorization") String authorization,
            @Header("X-GitHub-Api-Version") String apiVersion,
            @Body RepoPost repo
    );

    /**
     * Updates an existing repository for the authenticated user.
     *
     * @param user The user or organization owning the repository.
     * @param repoName The name of the repository to be updated.
     * @param contentType The content type of the request.
     * @param authorization The authorization token for the request.
     * @param apiVersion The GitHub API version.
     * @param repo The repository data with updates.
     * @return A Call object representing the asynchronous execution of the request.
     */
    @PATCH("/repos/{user}/{repo}")
    Call<GitHubRepo> updateRepo(
            @Path("user") String user,
            @Path("repo") String repoName,
            @Header("accept") String contentType,
            @Header("authorization") String authorization,
            @Header("X-GitHub-Api-Version") String apiVersion,
            @Body RepoPost repo
    );

    /**
     * Deletes an existing repository for the authenticated user.
     *
     * @param user The user or organization owning the repository.
     * @param repoName The name of the repository to be deleted.
     * @param contentType The content type of the request.
     * @param authorization The authorization token for the request.
     * @param apiVersion The GitHub API version.
     * @return A Call object representing the asynchronous execution of the request.
     */
    @DELETE("/repos/{user}/{repo}")
    Call<GitHubRepo> deleteRepo(
            @Path("user") String user,
            @Path("repo") String repoName,
            @Header("accept") String contentType,
            @Header("authorization") String authorization,
            @Header("X-GitHub-Api-Version") String apiVersion
    );

    /**
     * Gets an instance of GithubApiClient.
     *
     * @return An instance of GithubApiClient.
     */
    public static GithubApiClient getGithubApiClient(){
        Retrofit retrofit = RetrofitClient.getClient();
        return retrofit.create(GithubApiClient.class);
    }

    /**
     * Gets the authenticated user.
     *
     * @return The authenticated user.
     */
    public static String getUser(){
        return RetrofitClient.getUser();
    }

    /**
     * Gets the authentication token.
     *
     * @return The authentication token.
     */
    public static String getToken() {
        return RetrofitClient.getToken();
    }

    /**
     * Gets the GitHub API version.
     *
     * @return The GitHub API version.
     */
    public static String getApiVersion() { return RetrofitClient.getApiVersion(); }

    /**
     * Gets the content type for requests.
     *
     * @return The content type for requests.
     */
    public static String getContentType() { return RetrofitClient.getContentType(); }
}
