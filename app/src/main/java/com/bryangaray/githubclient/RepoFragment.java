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

public class RepoFragment extends Fragment {
    private TextView repoName, repoDescription, repoLanguage, repoCreationDate;
    private GitHubRepo repo;
    private RepoFragment(GitHubRepo repo){
        this.repo = repo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.repo_item, container, false);
        repoName = view.findViewById(R.id.repoName);
        repoDescription = view.findViewById(R.id.repoDescription);
        repoLanguage = view.findViewById(R.id.repoLanguage);
        repoCreationDate = view.findViewById(R.id.repoCreationDate);

        repoName.setText(this.repo.getName());
        repoDescription.setText(this.repo.getDescription());
        repoLanguage.setText(this.repo.getLanguage());
        repoCreationDate.setText(this.repo.getCreationDate());

        return view;
    }
}

