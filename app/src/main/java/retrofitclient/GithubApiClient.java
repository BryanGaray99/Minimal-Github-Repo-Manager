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

public interface GithubApiClient {
    /**
     * Endpoints for User and Repositories
     */
    @GET("/user")
    Call<GitHubUser> getUser(
            @Header("accept") String contentType,
            @Header("authorization") String authorization,
            @Header("X-GitHub-Api-Version") String apiVersion
    );

    @GET("/user/repos")
    Call<List<GitHubRepo>> getRepos(
            @Header("accept") String contentType,
            @Header("authorization") String authorization,
            @Header("X-GitHub-Api-Version") String apiVersion,
            @Query("per_page") int limit,
            @Query("sort") String order
    );

    @POST("/user/repos")
    Call<GitHubRepo> createRepo(
            @Header("accept") String contentType,
            @Header("authorization") String authorization,
            @Header("X-GitHub-Api-Version") String apiVersion,
            @Body RepoPost repo
    );

    @PATCH("/repos/{user}/{repo}")
    Call<GitHubRepo> updateRepo(
            @Path("user") String user,
            @Path("repo") String repoName,
            @Header("accept") String contentType,
            @Header("authorization") String authorization,
            @Header("X-GitHub-Api-Version") String apiVersion,
            @Body RepoPost repo
    );

    @DELETE("/repos/{user}/{repo}")
    Call<GitHubRepo> deleteRepo(
            @Path("user") String user,
            @Path("repo") String repoName,
            @Header("accept") String contentType,
            @Header("authorization") String authorization,
            @Header("X-GitHub-Api-Version") String apiVersion
    );

    public static GithubApiClient getGithubApiClient(){
        Retrofit retrofit = RetrofitClient.getClient();
        return retrofit.create(GithubApiClient.class);
    }
    public static String getUser(){
        return RetrofitClient.getUser();
    }
    public static String getToken() {
        return RetrofitClient.getToken();
    }
    public static String getApiVersion() { return RetrofitClient.getApiVersion(); }
    public static String getContentType() { return RetrofitClient.getContentType(); }
}
