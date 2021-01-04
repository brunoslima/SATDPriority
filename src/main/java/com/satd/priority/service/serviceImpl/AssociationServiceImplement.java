package com.satd.priority.service.serviceImpl;

import com.satd.priority.model.Association;
import com.satd.priority.repository.AssociationRepository;
import com.satd.priority.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociationServiceImplement implements AssociationService {

    @Autowired
    AssociationRepository associationRepository;

    @Override
    public List<Association> findAll() {
        return associationRepository.findAll();
    }
    /*
    @Override
    public Association findByIdSatd(long id) {
        return associationRepository.findById(id).get();
    }

    @Override
    public Association findByIdIssue(long id) {
        return associationRepository.findById(id).get();
    }
    */

    @Override
    public Association save(Association association) {
        return associationRepository.save(association);
    }

}
