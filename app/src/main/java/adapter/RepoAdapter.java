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

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {
    private List<GitHubRepo> repos;
    private Context context;
    private GithubApiClient apiClient = GithubApiClient.getGithubApiClient();
    public RepoAdapter(List<GitHubRepo> repos, Context context) {
        this.repos = repos;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.repo_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GitHubRepo repo = repos.get(position);
        holder.setReposData(repo);
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
    @Override
    public int getItemCount() {
        return repos.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView repoName, repoDescription, repoLanguage, repoCreationDate;
        private ImageButton btnUpdate, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            repoName = itemView.findViewById(R.id.repoName);
            repoDescription = itemView.findViewById(R.id.repoDescription);
            repoLanguage = itemView.findViewById(R.id.repoLanguage);
            repoCreationDate = itemView.findViewById(R.id.repoCreationDate);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
        public void setReposData(GitHubRepo repo) {
            repoName.setText(repo.getName());
            repoDescription.setText(repo.getDescription());
            repoLanguage.setText(repo.getLanguage());
            repoCreationDate.setText(repo.getCreationDate());
        }
    }
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
    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}