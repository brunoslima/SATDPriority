package com.satd.priority.utils;

import com.satd.priority.model.Term;
import com.satd.priority.repository.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class StartData {

    @Autowired
    TermRepository termRepository;

    //@PostConstruct
    public void saveTerms() throws FileNotFoundException {

        List<Term> termList = new ArrayList<>();

        String arq = "data\\terms.csv";
        String line = "";
        String elements[];

        Scanner readFileIssue = new Scanner(new BufferedReader(new FileReader(arq)));

        while (readFileIssue.hasNextLine()) {

            line = readFileIssue.nextLine();
            elements = line.split(";");

            //System.out.println(line + "\n\n");
            Term term = new Term();
            term.setTermDescription(elements[0]);

            termList.add(term);

        }//end while

        for(Term termInstance : termList){

            termRepository.save(termInstance);
            //Term termSaved = termRepository.save(termInstance);
            //System.out.println(termSaved.getTerm());
        }

    }//end saveTerms

}
