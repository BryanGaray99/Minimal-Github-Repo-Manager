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

/**
 * RepoPatchForm is an activity that allows users to update information for a GitHub repository.
 */
public class RepoPatchForm extends AppCompatActivity {
    private EditText repoNameEditText, repoDescriptionEditText;
    private Button updateButton;
    private GithubApiClient apiClient = GithubApiClient.getGithubApiClient();
    private String originalRepoName;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patch_form);

        repoNameEditText = findViewById(R.id.repoPatchName);
        repoDescriptionEditText = findViewById(R.id.repoPatchDescription);
        updateButton = findViewById(R.id.updateRepository);

        // Retrieve extras from the intent to pre-fill the form with existing repository information
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            originalRepoName = extras.getString("repoName", "");
            String repoDescription = extras.getString("repoDescription", "");
            repoNameEditText.setText(originalRepoName);
            repoDescriptionEditText.setText(repoDescription);
        }

        // Set up the button click listener to trigger the repository update
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRepo();
            }
        });
    }

    /**
     * Updates the repository information based on the user input.
     */
    private void updateRepo() {
        String newRepoName = repoNameEditText.getText().toString();
        String repoDescription = repoDescriptionEditText.getText().toString();

        if (!newRepoName.isEmpty()) {
            // Retrieve necessary parameters for API request
            String contentType = GithubApiClient.getContentType();
            String authorization = GithubApiClient.getToken();
            String apiVersion = GithubApiClient.getApiVersion();

            // Create RepoPost object with updated information
            RepoPost repoRequest = new RepoPost(newRepoName, repoDescription);

            // Make API call to update the repository
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
            showToast("Repository name cannot be empty");
        }
    }

    /**
     * Displays a Toast message with the given message.
     *
     * @param message The message to be displayed in the Toast.
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
