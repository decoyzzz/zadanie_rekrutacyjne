package com.zadanie.demo;

import com.zadanie.demo.dto.BranchDto;
import com.zadanie.demo.dto.RepositoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
	void shouldReturnRepositoriesForExistingUser() {
		String username = "decoyzzz";

		String url = "http://localhost:" + port + "/users/" + username;
		ResponseEntity<RepositoryDto[]> response = restTemplate.getForEntity(url, RepositoryDto[].class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		RepositoryDto[] repos = response.getBody();
		assertThat(repos).isNotNull();
		assertThat(repos.length).isGreaterThan(0);

		for (RepositoryDto repo : repos) {
			assertThat(repo.getRepositoryName()).isNotNull();
			assertThat(repo.getOwnerLogin()).isEqualTo(username);

			List<BranchDto> branches = repo.getBranches();
			assertThat(branches).isNotNull();
			assertThat(branches.size()).isGreaterThan(0);

			for (BranchDto branch : branches) {
				assertThat(branch.getName()).isNotBlank();
				assertThat(branch.getLastCommitSha()).isNotBlank();
			}
		}
	}

}
