package com.satd.priority.service;

import com.satd.priority.model.Term;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TermService {

    //List<Term> findAll();
    Page<Term> findAll();
    Term findById(long id);
    Term save(Term term);

}
