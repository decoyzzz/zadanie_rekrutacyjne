package com.zadanie.demo.dto;

public class BranchDto {
    private String name;
    private String lastCommitSha;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastCommitSha() { return lastCommitSha; }
    public void setLastCommitSha(String lastCommitSha) { this.lastCommitSha = lastCommitSha; }
}
