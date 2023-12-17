# Minimal-Github-Repo-Manager

# GitHub Client App

This Android app allows you to manage GitHub repositories using the GitHub API.

## Overview

The GitHub Client App is designed to provide a mobile interface for users to interact with their GitHub repositories. It leverages the GitHub API to retrieve information about the authenticated user, list repositories, create new repositories, update existing repositories, and delete repositories.

## Functionalities

The app supports the following functionalities:

- **View User Information:** Retrieve information about the authenticated GitHub user, including their profile image, name, and username.

- **List Repositories:** Display a list of repositories owned by the authenticated user. Each repository is presented with its name, description, primary language, and creation date.

- **Create Repository:** Create a new repository by providing a name and an optional description.

- **Update Repository:** Modify the name and description of an existing repository.

- **Delete Repository:** Permanently delete a repository.

## Supported Endpoints

The app interacts with the following GitHub API endpoints:

- **Get User Information:**
    - Endpoint: `/user`
    - Method: `GET`

- **List Repositories:**
    - Endpoint: `/user/repos`
    - Method: `GET`

- **Create Repository:**
    - Endpoint: `/user/repos`
    - Method: `POST`

- **Update Repository:**
    - Endpoint: `/repos/{user}/{repo}`
    - Method: `PATCH`

- **Delete Repository:**
    - Endpoint: `/repos/{user}/{repo}`
    - Method: `DELETE`

## Building and Running

1. Open the project in Android Studio.

2. Build and run the app on your emulator or physical device.

3. Explore and manage your GitHub repositories using the app.

## Notes

- Ensure that you have internet connectivity for the app to communicate with the GitHub API.

- Be cautious with your GitHub token, and do not expose it publicly.

- If you encounter any issues, feel free to open an issue on the GitHub repository.

## Setup

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/github-client-app.git
    ```

2. Navigate to the project directory:

    ```bash
    cd github-client-app
    ```

3. Create a new file named `env` in the `src/main/assets/` directory. This file should contain your GitHub username and token. Example:

    ```
    GITHUB_USER="your-username"
    GITHUB_TOKEN="your-github-token"
    ```

   Make sure to replace "your-username" and "your-github-token" with your GitHub username and token.

4. (Optional) Create an example `envExample` file for others to reference:

    ```bash
    cp src/main/assets/env src/main/assets/envExample
    ```

   This step is optional but can be useful for other developers to know the expected format of the `env` file.


## Author: Bryan Enrique Garay Benavidez

## Contact me: bryangarayacademico@gmail.com