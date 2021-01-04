package com.satd.priority.service;

import com.satd.priority.model.Association;
import java.util.List;

public interface AssociationService {

    List<Association> findAll();
    //Association findByIdSatd(long idSatd);
    //Association findByIdIssue(long idIssue);
    Association save(Association association);

}
