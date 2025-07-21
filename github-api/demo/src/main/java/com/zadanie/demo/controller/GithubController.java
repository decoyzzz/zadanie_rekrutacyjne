package com.zadanie.demo.controller;

import com.zadanie.demo.dto.RepositoryDto;
import com.zadanie.demo.service.GithubService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService)
    {
        this.githubService = githubService;
    }

    @GetMapping("/{username}")
    public List<RepositoryDto> getUserRepos(@PathVariable String username)
    {
        return githubService.getNonForkedReposWithBranches(username);
    }
}
