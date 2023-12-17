package com.bryangaray.githubclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import adapter.RepoAdapter;
import models.GitHubRepo;
import models.GitHubUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofitclient.GithubApiClient;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RepoAdapter repoAdapter;
    private List<GitHubRepo> repos = new ArrayList<>();
    private FloatingActionButton fabRepoForm;
    private Button btnReload;
    private ImageView userImage;
    private TextView repoOwner, repoUsername;
    private Bitmap myImage;
    private GithubApiClient apiClient = GithubApiClient.getGithubApiClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        repoOwner = findViewById(R.id.repoOwner);
        repoUsername = findViewById(R.id.repoUsername);
        userImage = findViewById(R.id.userImage);

        btnReload = findViewById(R.id.refreshButton);
        Handler handler = new Handler();

        AnimationDrawable d = (AnimationDrawable)btnReload.getCompoundDrawables()[0];
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.start();
                loadRepos();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        d.stop();
                    }
                }, 3000);
            }
        });

        fabRepoForm = findViewById(R.id.fabNewRepo);
        fabRepoForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RepoPostForm.class);
                startActivity(intent);
            }
        });
        this.loadRepos();
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.loadUser();
        this.loadRepos();
    }

    public void loadUser() {
        String contentType  = GithubApiClient.getContentType();
        String authorization = GithubApiClient.getToken();
        String apiVersion = GithubApiClient.getApiVersion();

        Call<GitHubUser> call = apiClient.getUser(
                contentType,
                authorization,
                apiVersion);

        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                if (response.isSuccessful()) {
                    ImageDownloader task = new ImageDownloader();
                    try {
                        myImage = task.execute(response.body().getUserImage()).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    userImage.setImageBitmap(myImage);

                    if(response.body().getRepoOwner() == null){
                        repoOwner.setText("No name provided");
                    } else {
                        repoOwner.setText(response.body().getRepoOwner());
                    }

                    repoUsername.setText(response.body().getRepoUsername());
                } else {
                    showToast("Server error, user data was not found");
                }
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {
                showToast("Server error, repository was not deleted");
            }
        });
    }

    /**
     * Load Repos
     **/
    private void loadRepos(){
        String contentType  = GithubApiClient.getContentType();
        String authorization = GithubApiClient.getToken();
        String apiVersion = GithubApiClient.getApiVersion();

        Call<List<GitHubRepo>> call = apiClient.getRepos(
                contentType,
                authorization,
                apiVersion,
                15,
                "create");
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {

                if (response.isSuccessful()){
                    repos = response.body();
                    if (repos!=null){
                        displayList();
                    }else{
                        showToast("Server error, repositories didn´t load");
                    }
                }else{
                    showToast("Server error, repositories didn´t load");
                }

            }
            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                showToast("Server error");
            }
        });

    }

    private void displayList(){
        repoAdapter = new RepoAdapter(repos,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(repoAdapter);
    }

    /**
     * Toast messages function to reuse
     * @param message
     */
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }
            return null;
        }
    }

}
