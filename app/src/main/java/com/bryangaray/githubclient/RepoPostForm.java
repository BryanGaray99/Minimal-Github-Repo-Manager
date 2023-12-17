package com.bryangaray.githubclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import models.GitHubRepo;
import models.RepoPost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofitclient.GithubApiClient;


public class RepoPostForm extends AppCompatActivity {
    private EditText repoName, repoDescription;
    private Button btnSave;
    private GithubApiClient apiClient = GithubApiClient.getGithubApiClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_form);

        repoName = findViewById(R.id.repoPostName);
        repoDescription = findViewById(R.id.repoPostDescription);
        btnSave = findViewById(R.id.saveButton);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRepo();
            }
        });
    }

    /**
     * POST album
     */
    private void saveRepo() {
        String repoNameText = repoName.getText().toString();
        String repoDescriptionText = repoDescription.getText().toString();

        if (!repoNameText.isEmpty()) {

            String contentType = GithubApiClient.getContentType();
            String authorization = GithubApiClient.getToken();
            String apiVersion = GithubApiClient.getApiVersion();

            RepoPost repoRequest = new RepoPost(repoNameText, repoDescriptionText);
            Call<GitHubRepo> call = apiClient.createRepo(
                    contentType,
                    authorization,
                    apiVersion,
                    repoRequest);

            call.enqueue(new Callback<GitHubRepo>() {
                @Override
                public void onResponse(Call<GitHubRepo> call, Response<GitHubRepo> response) {
                    if (response.isSuccessful()) {
                        showToast("Repository successfully created");
                        finish();
                    } else {
                        showToast("Server error, repository was not created");
                    }
                }
                @Override
                public void onFailure(Call<GitHubRepo> call, Throwable t) {
                    showToast("Server error, repository was not created");
                }
            });
        } else {
            showToast("Repository name can not be empty");
        }
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}