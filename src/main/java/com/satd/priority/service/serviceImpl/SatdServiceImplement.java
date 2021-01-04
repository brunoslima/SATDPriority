package com.satd.priority.service.serviceImpl;

import com.satd.priority.model.Satd;
import com.satd.priority.repository.SatdRepository;
import com.satd.priority.service.SatdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SatdServiceImplement implements SatdService {

    @Autowired
    SatdRepository satdRepository;

    @Override
    public Page<Satd> findAll() {
        Pageable pageable = PageRequest.of(0,10);
        return satdRepository.findAll(pageable);
    }

    @Override
    public Satd findById(long id) {
        return satdRepository.findById(id).get();
    }

    @Override
    public Satd save(Satd satd) {
        return satdRepository.save(satd);
    }

}
