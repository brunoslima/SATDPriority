package com.satd.priority.service.serviceImpl;

import com.satd.priority.model.Term;
import com.satd.priority.repository.TermRepository;
import com.satd.priority.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermServiceImplement implements TermService {

    @Autowired
    TermRepository termRepository;

    /*@Override
    public List<Term> findAll() {
        return termRepository.findAll();
    }*/

    @Override
    public Page<Term> findAll() {
        Pageable pageable = PageRequest.of(0,10);
        return termRepository.findAll(pageable);
    }

    @Override
    public Term findById(long id) {
        return termRepository.findById(id).get();
    }

    @Override
    public Term save(Term term) {
        return termRepository.save(term);
    }

}
