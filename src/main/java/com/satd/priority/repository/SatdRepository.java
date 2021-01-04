package com.satd.priority.repository;

import com.satd.priority.model.Satd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

//public interface SatdRepository extends JpaRepository<Satd, Long> {
public interface SatdRepository extends PagingAndSortingRepository<Satd, Long> {

}
