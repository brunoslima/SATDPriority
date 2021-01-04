package com.satd.priority.repository;

import com.satd.priority.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {

}
