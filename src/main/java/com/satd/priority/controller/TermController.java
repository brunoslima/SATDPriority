package com.satd.priority.controller;

import com.satd.priority.core.ContextIdentifier;
import com.satd.priority.core.Core;
import com.satd.priority.core.Data;
import com.satd.priority.core.FileApplication;
import com.satd.priority.model.Association;
import com.satd.priority.model.Satd;
import com.satd.priority.model.Term;
import com.satd.priority.service.*;
import com.satd.priority.service.serviceImpl.IssueServiceImplement;
import com.satd.priority.service.serviceImpl.SatdServiceImplement;
import org.apache.commons.io.LineIterator;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Controller
public class TermController {

    Data data = new Data();

    @Autowired
    FileService fileService;

    @Autowired
    TermService termService;

    @Autowired
    SatdService satdService;

    @Autowired
    IssueService issueService;

    @Autowired
    AssociationService associationService;

    @GetMapping("/")
    public String index() {
        return "priority";
    }

    @RequestMapping(value = "/home")
    public ModelAndView homeView(){

        ModelAndView mv = new ModelAndView("home");
        return mv;

    }

    @RequestMapping(value = "/priority")
    public ModelAndView priorityView(){

        ModelAndView mv = new ModelAndView("priority");
        return mv;

    }

    @RequestMapping(value = "/priorityList", method = RequestMethod.GET)
    public ModelAndView priorityListView(){

        ModelAndView mv = new ModelAndView("priorityList");

        Page<Satd> page = satdService.findAll();
        long totalElements = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Satd> list = page.getContent();

        mv.addObject("totalElements", totalElements);
        mv.addObject("totalPages", totalPages);
        mv.addObject("list", list);
        return mv;

    }

    @RequestMapping(value = "/terms", method = RequestMethod.GET)
    public ModelAndView getTerms(){

        ModelAndView mv = new ModelAndView("terms");

        Page<Term> page = termService.findAll();
        long totalElements = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Term> terms = page.getContent();

        mv.addObject("totalElements", totalElements);
        mv.addObject("totalPages", totalPages);
        mv.addObject("terms", terms);
        return mv;

    }

    /*@RequestMapping(value = "/terms", method = RequestMethod.GET)
    public ModelAndView getTerms(){

        ModelAndView mv = new ModelAndView("terms");
        List<Term> terms = termService.findAll();
        mv.addObject("terms", terms);
        return mv;

    }*/

    @RequestMapping(value = "/terms", method = RequestMethod.POST)
    public String saveTerm(Term term, RedirectAttributes attributes){

        termService.save(term);
        return "redirect:/terms";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile[] files, RedirectAttributes redirectAttributes) throws FileNotFoundException {

        //fileService.uploadFile(file);

        Arrays.asList(files).stream().forEach(file -> fileService.uploadFile(file));
        String nameFile1 = Arrays.asList(files).get(0).getOriginalFilename();
        String nameFile2 = Arrays.asList(files).get(1).getOriginalFilename();

        FileApplication.readSATDMultipartFile(Arrays.asList(files).get(0));
        FileApplication.readISSUEMultipartFile(Arrays.asList(files).get(1));

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + nameFile1 + " and " + nameFile2 + "!");

        //Efetice Priorization
        FileApplication.readSATD("data\\upload\\satd-list.csv", this.data);
        FileApplication.readISSUES("data\\upload\\issue-list.csv", this.data);

        ContextIdentifier.searchSATDLocation(data);

        Core core = new Core();
        //core.makeAssociationLimiar(data,5);
        core.makeAssociationContext(data);
        core.makePriorizationContext();
        //core.showPriority();
        System.out.println("Amount association: " + core.getAssociationCollection().size());
        int cont = 0;
        for(int i = 0; i < core.getAssociationCollection().size(); i++) {

            if(core.getAssociationCollection().get(i).getIssuesInstances().size() != 0) cont++;
        }
        System.out.println("Amount association with issues: " + cont);

        try {
            FileApplication.writePriorityList("data\\result\\priority-list.csv", core);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Map<String,String> valuesIssues = new HashMap<String,String>();


        for(int i = 0; i < core.getAssociationCollection().size(); i++){

            //save satd instances in database
            double priorityNivel = core.getAssociationCollection().get(i).getPriorityNivel();
            core.getAssociationCollection().get(i).getSatdInstance().setPriority(priorityNivel);
            satdService.save(core.getAssociationCollection().get(i).getSatdInstance());

            //save issue instances in database
            for (int j = 0; j < core.getAssociationCollection().get(i).getIssuesInstances().size(); j++){

                Long idSatd = core.getAssociationCollection().get(i).getSatdInstance().getId();
                core.getAssociationCollection().get(i).getIssuesInstances().get(j).setIdSatd(idSatd);

                String issue = core.getAssociationCollection().get(i).getIssuesInstances().get(j).getDescription();
                issue += " - " + core.getAssociationCollection().get(i).getIssuesInstances().get(j).getPath();
                issue += " - " + core.getAssociationCollection().get(i).getIssuesInstances().get(j).getResource();
                issue += " - " + core.getAssociationCollection().get(i).getIssuesInstances().get(j).getLine();

                if(!valuesIssues.containsKey(issue)){
                    valuesIssues.put(issue, issue);
                    issueService.save(core.getAssociationCollection().get(i).getIssuesInstances().get(j));
                }

                Long idIssue = core.getAssociationCollection().get(i).getIssuesInstances().get(j).getId();

                Association association = new Association(idSatd, idIssue);
                associationService.save(association);

            }

        }

        core.show();

        return "redirect:/";
    }

    @RequestMapping(value = "/priority", method = RequestMethod.POST)
    public String executePriorization(){

        System.out.println("OLAAAAAAA ->>>>>>>>>>>>>>>>>");
        return "redirect:/home";
    }

}
