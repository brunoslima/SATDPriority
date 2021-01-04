package com.satd.priority.service;

import com.satd.priority.model.Satd;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SatdService {

    Page<Satd> findAll();
    Satd findById(long id);
    Satd save(Satd satd);

}
