package com.satd.priority.core;

import com.satd.priority.model.Issue;
import com.satd.priority.model.Satd;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileApplication {

    public static void readSATDMultipartFile(MultipartFile file) {

        BufferedReader br;
        List<String> result = new ArrayList<>();
        try {

            String line;
            String elements[];
            Map<String,String> valuesLineSATD = new HashMap<String,String>();
            int cont = 0;

            FileWriter arq = null;
            try {
                arq = new FileWriter("data\\upload\\satd-list.csv");
            } catch (IOException ex) {
                Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            }
            PrintWriter record = new PrintWriter(arq);
            //record.println("SATD;Class;Path;Line;Priority");

            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {

                //System.out.println(line);

                elements = line.split(";");
                line = elements[0] + ";" + elements[1] + ";" + elements[2] + ";" + elements[3] + ";" + "0";

                if(cont == 0) {
                    //valuesLineSATD.put(line,line);
                    //record.println(line);
                    //System.out.println(line);
                }
                else if(!valuesLineSATD.containsKey(line)){

                    valuesLineSATD.put(line,line);
                    record.println(line);
                    //System.out.println(line);
                }
                /*
                record.print(elements[0] + ";");
                record.print(elements[1] + ";");
                record.print(elements[2] + ";");
                record.print(elements[3] + ";");
                record.println("0");
                */
                cont++;

            }

            arq.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public static void readISSUEMultipartFile(MultipartFile file) {

        BufferedReader br;
        List<String> result = new ArrayList<>();
        try {

            String line;
            String elements[];

            FileWriter arq = null;
            try {
                arq = new FileWriter("data\\upload\\issue-list.csv");
            } catch (IOException ex) {
                Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            }
            PrintWriter record = new PrintWriter(arq);
            //record.println("title;description;path;class;line;rework;severity");

            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {

                elements = line.split(";");

                record.print(elements[0] + ";");
                record.print(elements[1] + ";");
                record.print(elements[2] + ";");
                record.print(elements[3] + ";");
                record.print(elements[4] + ";");
                record.print(elements[5] + ";");
                record.println(elements[6]);
            }

            arq.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public static void readSATD(String arq, Data data) throws FileNotFoundException {

        String line = "";
        String elements[];
        Scanner readFileSatd = new Scanner(new BufferedReader(new FileReader(arq)));

        while (readFileSatd.hasNextLine()) {

            line = readFileSatd.nextLine();
            elements = line.split(";");
            //System.out.println(line + "\n\n");
            Satd satdInstance = new Satd(elements[0],elements[1],elements[2],elements[3],elements[4]);
            data.getSatdCollection().add(satdInstance);
        }
    }

    public static void readISSUES(String arq, Data data) throws FileNotFoundException {

        String line = "";
        String elements[];
        Scanner readFileIssue = new Scanner(new BufferedReader(new FileReader(arq)));

        while (readFileIssue.hasNextLine()) {

            line = readFileIssue.nextLine();
            elements = line.split(";");

            //System.out.println(line + "\n\n");
            Issue issueInstance = new Issue(elements[0],elements[1],elements[2],elements[3],elements[4],elements[5],elements[6]);
            data.getIssueCollection().add(issueInstance);
        }
    }

    public static void readClass(String arq, ClassJava javaFile) throws FileNotFoundException {

        //String line = "";
        Scanner readFileSatd = new Scanner(new BufferedReader(new FileReader(arq)));

        while (readFileSatd.hasNextLine()) {

            javaFile.getLines().add(readFileSatd.nextLine());

            //System.out.println(line);
        }
    }

    public static void writePriorityList(String name, Core core) throws IOException {

        FileWriter arq = null;
        try {
            arq = new FileWriter(name);
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintWriter record = new PrintWriter(arq);

        record.println("SATD;Path;Line;Priority");
        for (int i = 0; i < core.getAssociationCollection().size(); i++) {

            record.print(core.getAssociationCollection().get(i).getSatdInstance().getDescription() + ";");
            record.print(core.getAssociationCollection().get(i).getSatdInstance().getPath() + ";");
            record.print(core.getAssociationCollection().get(i).getSatdInstance().getLine() + ";");
            record.println(core.getAssociationCollection().get(i).getPriorityNivel());

        }

        arq.close();
    }


}
