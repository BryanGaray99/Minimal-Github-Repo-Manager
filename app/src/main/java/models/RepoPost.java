package models;

import com.google.gson.annotations.SerializedName;

public class RepoPost {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;

    public RepoPost(String name, String description) {
        this.setName(name);
        this.setDescription(description);
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
}
