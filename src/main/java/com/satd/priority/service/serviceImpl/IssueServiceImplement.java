package com.satd.priority.service.serviceImpl;

import com.satd.priority.model.Issue;
import com.satd.priority.repository.IssueRepository;
import com.satd.priority.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceImplement implements IssueService {

    @Autowired
    IssueRepository issueRepository;

    @Override
    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    @Override
    public Issue findById(long id) {
        return issueRepository.findById(id).get();
    }

    @Override
    public Issue save(Issue issue) {
        return issueRepository.save(issue);
    }

}
