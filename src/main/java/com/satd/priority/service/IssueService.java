package com.satd.priority.service;

import com.satd.priority.model.Issue;

import java.util.List;

public interface IssueService {

    List<Issue> findAll();
    Issue findById(long id);
    Issue save(Issue issue);

}
