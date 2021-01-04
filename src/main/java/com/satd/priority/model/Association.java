package com.satd.priority.model;

import javax.persistence.*;

@Entity
@Table(name="ASSOCIATION")
public class Association {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long idSatd;
    private Long idIssue;

    public Association(Long idSatd, Long idIssue){

        this.idSatd = idSatd;
        this.idIssue = idIssue;
    }

    public long getIdSatd() {
        return idSatd;
    }

    public void setIdSatd(long idSatd) {
        this.idSatd = idSatd;
    }

    public long getIdIssue() {
        return idIssue;
    }

    public void setIdIssue(long idIssue) {
        this.idIssue = idIssue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
