package com.zadanie.demo.dto;

import java.util.List;

public class RepositoryDto {
    private String repositoryName;
    private String ownerLogin;
    private List<BranchDto> branches;

    public String getRepositoryName() { return repositoryName; }
    public void setRepositoryName(String name) { this.repositoryName = name; }

    public String getOwnerLogin() { return ownerLogin; }
    public void setOwnerLogin(String ownerLogin) { this.ownerLogin = ownerLogin; }

    public List<BranchDto> getBranches() { return branches; }
    public void setBranches(List<BranchDto> branches) { this.branches = branches; }
}
