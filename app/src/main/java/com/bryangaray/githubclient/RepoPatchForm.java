package com.bryangaray.githubclient;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import models.GitHubRepo;
import models.RepoPost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofitclient.GithubApiClient;
public class RepoPatchForm extends AppCompatActivity {
    private EditText repoNameEditText, repoDescriptionEditText;
    private Button updateButton;
    private GithubApiClient apiClient = GithubApiClient.getGithubApiClient();
    private String originalRepoName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patch_form);

        repoNameEditText = findViewById(R.id.repoPatchName);
        repoDescriptionEditText = findViewById(R.id.repoPatchDescription);
        updateButton = findViewById(R.id.updateRepository);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            originalRepoName = extras.getString("repoName", "");
            String repoDescription = extras.getString("repoDescription", "");
            repoNameEditText.setText(originalRepoName);
            repoDescriptionEditText.setText(repoDescription);
        }
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRepo();
            }
        });
    }
    private void updateRepo() {
        String newRepoName = repoNameEditText.getText().toString();
        String repoDescription = repoDescriptionEditText.getText().toString();

        if (!newRepoName.isEmpty()) {

            String contentType = GithubApiClient.getContentType();
            String authorization = GithubApiClient.getToken();
            String apiVersion = GithubApiClient.getApiVersion();

            RepoPost repoRequest = new RepoPost(newRepoName, repoDescription);
            Call<GitHubRepo> call = apiClient.updateRepo(
                    GithubApiClient.getUser(),
                    originalRepoName,
                    contentType,
                    authorization,
                    apiVersion,
                    repoRequest);

            call.enqueue(new Callback<GitHubRepo>() {
                @Override
                public void onResponse(Call<GitHubRepo> call, Response<GitHubRepo> response) {
                    if (response.isSuccessful()) {
                        showToast("Repository successfully updated");
                        finish();
                    } else {
                        showToast("Server error, repository was not updated");
                    }
                }
                @Override
                public void onFailure(Call<GitHubRepo> call, Throwable t) {
                    showToast("Server error, repository was not updated");
                }
            });
        } else {
            showToast("Repository name can not be empty");
        }
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}