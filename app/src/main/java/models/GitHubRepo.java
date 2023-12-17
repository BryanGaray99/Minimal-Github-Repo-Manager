package models;

import com.google.gson.annotations.SerializedName;

public class GitHubRepo {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("language")
    private String language;
    @SerializedName("created_at")
    private String creationDate;

    public GitHubRepo(String language, String description,  String name, String creationDate) {
        this.setLanguage(language);
        this.setDescription(description);
        this.setName(name);
        this.setCreationDate(creationDate);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
