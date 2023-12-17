package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bryangaray.githubclient.R;
import com.bryangaray.githubclient.RepoPatchForm;

import java.util.List;

import models.GitHubRepo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofitclient.GithubApiClient;

/**
 * RepoAdapter is a RecyclerView adapter for displaying a list of GitHub repositories.
 */
public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {
    private List<GitHubRepo> repos;
    private Context context;
    private GithubApiClient apiClient = GithubApiClient.getGithubApiClient();

    /**
     * Constructs a new RepoAdapter.
     *
     * @param repos   List of GitHub repositories to be displayed.
     * @param context The context of the application.
     */
    public RepoAdapter(List<GitHubRepo> repos, Context context) {
        this.repos = repos;
        this.context = context;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The type of the new View.
     * @return A new ViewHolder that holds a View with the provided layout resource.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.repo_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder that should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GitHubRepo repo = repos.get(position);
        holder.setReposData(repo);

        /**
         * Control the navigation to RepoPatchForm when button is clicked
         */
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    GitHubRepo clickedRepo = repos.get(adapterPosition);
                    Intent intent = new Intent(context, RepoPatchForm.class);
                    intent.putExtra("repoName", clickedRepo.getName());
                    intent.putExtra("repoDescription", clickedRepo.getDescription());
                    context.startActivity(intent);
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(repo);
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return repos.size();
    }

    /**
     * ViewHolder class to hold the views for each item in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView repoName, repoDescription, repoLanguage, repoCreationDate;
        private ImageButton btnUpdate, btnDelete;

        /**
         * Constructs a new ViewHolder.
         *
         * @param itemView The View that will be hosted in this ViewHolder.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            repoName = itemView.findViewById(R.id.repoName);
            repoDescription = itemView.findViewById(R.id.repoDescription);
            repoLanguage = itemView.findViewById(R.id.repoLanguage);
            repoCreationDate = itemView.findViewById(R.id.repoCreationDate);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        /**
         * Sets the data of the ViewHolder with the given GitHubRepo.
         *
         * @param repo The GitHub repository data to be displayed.
         */
        public void setReposData(GitHubRepo repo) {
            repoName.setText(repo.getName());
            repoDescription.setText(repo.getDescription());
            repoLanguage.setText(repo.getLanguage());
            repoCreationDate.setText(repo.getCreationDate());
        }
    }

    /**
     * Shows a delete confirmation dialog for the specified GitHub repository.
     *
     * @param repo The GitHub repository to be deleted.
     */
    private void showDeleteConfirmationDialog(final GitHubRepo repo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¿Are you sure to delete this repository? This action is permanent!")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRepo(repo);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    /**
     * Deletes the specified GitHub repository.
     *
     * @param repo The GitHub repository to be deleted.
     */
    private void deleteRepo(final GitHubRepo repo) {
        String contentType = GithubApiClient.getContentType();
        String authorization = GithubApiClient.getToken();
        String apiVersion = GithubApiClient.getApiVersion();

        Call<GitHubRepo> call = apiClient.deleteRepo(
                GithubApiClient.getUser(),
                repo.getName(),
                contentType,
                authorization,
                apiVersion);

        call.enqueue(new Callback<GitHubRepo>() {
            @Override
            public void onResponse(Call<GitHubRepo> call, Response<GitHubRepo> response) {
                if (response.isSuccessful()) {
                    repos.remove(repo);
                    notifyDataSetChanged();
                    showToast("Repository successfully deleted");
                } else {
                    showToast("Server error, repository was not deleted");
                }
            }

            @Override
            public void onFailure(Call<GitHubRepo> call, Throwable t) {
                showToast("Server error, repository was not deleted");
            }
        });
    }

    /**
     * Displays a Toast message with the given message.
     *
     * @param message The message to be displayed in the Toast.
     */
    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
