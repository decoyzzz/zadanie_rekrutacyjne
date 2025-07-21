package com.zadanie.demo.service;

import com.zadanie.demo.dto.BranchDto;
import com.zadanie.demo.dto.RepositoryDto;
import com.zadanie.demo.exception.UserNotFoundException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class GithubService {

    private final RestTemplate restTemplate;

    public GithubService() {
        this.restTemplate = new RestTemplate();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "MyApp"); // dowolna nazwa klienta
        return headers;
    }

    public List<RepositoryDto> getNonForkedReposWithBranches(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";

        try {
            HttpEntity<String> entity = new HttpEntity<>(createHeaders());

            ResponseEntity<Map[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map[].class);
            List<Map<String, Object>> repos = Arrays.asList(response.getBody());

            List<RepositoryDto> result = new ArrayList<>();

            for (Map<String, Object> repo : repos) {
                boolean isFork = (Boolean) repo.get("fork");
                if (!isFork) {
                    String repoName = (String) repo.get("name");
                    Map<String, Object> owner = (Map<String, Object>) repo.get("owner");
                    String ownerLogin = (String) owner.get("login");

                    String branchesUrl = "https://api.github.com/repos/" + username + "/" + repoName + "/branches";
                    ResponseEntity<Map[]> branchesResponse = restTemplate.exchange(branchesUrl, HttpMethod.GET, entity, Map[].class);
                    List<Map<String, Object>> branches = Arrays.asList(branchesResponse.getBody());

                    List<BranchDto> branchDtos = new ArrayList<>();
                    for (Map<String, Object> branch : branches) {
                        String branchName = (String) branch.get("name");
                        Map<String, Object> commit = (Map<String, Object>) branch.get("commit");
                        String sha = (String) commit.get("sha");

                        BranchDto branchDto = new BranchDto();
                        branchDto.setName(branchName);
                        branchDto.setLastCommitSha(sha);
                        branchDtos.add(branchDto);
                    }

                    RepositoryDto dto = new RepositoryDto();
                    dto.setRepositoryName(repoName);
                    dto.setOwnerLogin(ownerLogin);
                    dto.setBranches(branchDtos);

                    result.add(dto);
                }
            }

            return result;

        } catch (HttpClientErrorException e) {

            if(e.getStatusCode() == HttpStatus.NOT_FOUND)
            {
                throw new UserNotFoundException("User doesn't exist");
            }
            else
            {
                throw e;
            }
            
        }
    }
}
