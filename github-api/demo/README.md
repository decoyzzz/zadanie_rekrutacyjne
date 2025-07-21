# GitHub repos API with error 404 handling

This application fetches public repositories of a given GitHub user, filters out forked repositories, and returns repository details along with their branches and last commit SHA. It also handles the case when the user does not exist by returning a 404 error with a clear message.

# How to run

1. Clone the repository from https://github.com/decoyzzz/zadanie_rekrutacyjne.git

2. Open the project directory
./github-api/demo

3. Run the application
./mvnw spring-boot:run

4. The API will be avaliable at http://localhost:8080/users/{username}

# Test
To run the integration test, use:
./mvnw test

## Environment

- Java 21  
- Spring Boot 3.5.3