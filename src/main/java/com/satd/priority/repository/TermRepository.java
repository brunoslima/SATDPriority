package com.satd.priority.repository;

import com.satd.priority.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

//public interface TermRepository extends JpaRepository<Term, Long>{
public interface TermRepository extends PagingAndSortingRepository<Term, Long> {

}
