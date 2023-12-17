package com.bryangaray.githubclient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import models.GitHubRepo;

/**
 * RepoFragment is a Fragment class representing the detailed view of a GitHub repository.
 * It displays information about the repository, such as name, description, language, and creation date.
 */
public class RepoFragment extends Fragment {
    private TextView repoName, repoDescription, repoLanguage, repoCreationDate;
    private GitHubRepo repo;

    /**
     * Constructs a new RepoFragment with the specified GitHubRepo.
     *
     * @param repo The GitHub repository for which the details are displayed.
     */
    private RepoFragment(GitHubRepo repo) {
        this.repo = repo;
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repo_item, container, false);
        repoName = view.findViewById(R.id.repoName);
        repoDescription = view.findViewById(R.id.repoDescription);
        repoLanguage = view.findViewById(R.id.repoLanguage);
        repoCreationDate = view.findViewById(R.id.repoCreationDate);

        // Set repository information to respective TextViews
        repoName.setText(this.repo.getName());
        repoDescription.setText(this.repo.getDescription());
        repoLanguage.setText(this.repo.getLanguage());
        repoCreationDate.setText(this.repo.getCreationDate());

        return view;
    }
}
