package models;

import com.google.gson.annotations.SerializedName;

public class GitHubUser {
    @SerializedName("login")
    private String repoUsername;
    @SerializedName("name")
    private String repoOwner;
    @SerializedName("avatar_url")
    private String userImage;

    public GitHubUser(String repoUsername, String repoOwner, String userImage) {
        this.repoUsername = repoUsername;
        this.repoOwner = repoOwner;
        this.userImage = userImage;
    }

    public String getRepoUsername() {
        return repoUsername;
    }
    public void setRepoUsername(String repoUsername) {
        this.repoUsername = repoUsername;
    }
    public String getRepoOwner() {
        return repoOwner;
    }
    public void setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
    }
    public String getUserImage() {
        return userImage;
    }
    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
