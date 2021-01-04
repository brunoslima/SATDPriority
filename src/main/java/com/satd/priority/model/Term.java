package com.satd.priority.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@Table(name="TERM")
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@NotNull
    private String termDescription;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTermDescription() {
        return this.termDescription;
    }

    public void setTermDescription(String termDescription) {
        this.termDescription = termDescription;
    }

}
