package com.satd.priority.core;

import com.satd.priority.model.Context;
import com.satd.priority.model.Satd;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ContextIdentifier {

    public static void searchSATDLocation(Data data) throws FileNotFoundException {

        String pathLocation, elements[], stringLineSATD, stringFirstLineSATD, stringFinalLineSATD;
        int firstLineSATD, finalLineSATD;

        for(int i = 0; i < data.getSatdCollection().size(); i++){

            ClassJava javaFile = new ClassJava();

            pathLocation = ContextIdentifier.getPathSATD(data.getSatdCollection().get(i).getPath());

            FileApplication.readClass(pathLocation, javaFile);

            stringLineSATD = data.getSatdCollection().get(i).getLine();
            elements = stringLineSATD.split("-");
            if(elements.length == 1){
                stringFirstLineSATD = elements[0];
                stringFinalLineSATD = elements[0];
            }
            else {
                stringFirstLineSATD = elements[0];
                stringFinalLineSATD = elements[1];
            }
            firstLineSATD = Integer.parseInt(stringFirstLineSATD);
            finalLineSATD = Integer.parseInt(stringFinalLineSATD);

            //System.out.println(finalLineSATD + " " + javaFile.getLines().get(finalLineSATD-1));

            searchContext(data.getSatdCollection().get(i), finalLineSATD, javaFile);
            //System.out.println(data.getSatdCollection().get(i).show());
            //for(int t = 0; t < data.getSatdCollection().get(i).getContextInstances().size(); t++) {
            //	System.out.println(data.getSatdCollection().get(i).getContextInstances().get(t).show());
            //}

            //TEST CLASS
            //classJava javaFileTest = new classJava();
            //file.readClass("testClass.java", javaFileTest);
            //searchContext(data.getSatdCollection().get(i), 3, javaFileTest);
            //System.out.println(data.getSatdCollection().get(i).show());
            //System.out.println(data.getSatdCollection().get(i).getContextInstances().get(0).show());

            //TEST METHOD
            //classJava javaFileTest = new classJava();
            //file.readClass("testMethod.java", javaFileTest);
            //searchContext(data.getSatdCollection().get(i), 5, javaFileTest);
            //javaFileTest.show(); //Come�a em zero ARRAY!!!
            //System.out.println(data.getSatdCollection().get(i).show());
            //System.out.println(data.getSatdCollection().get(i).getContextInstances().get(0).show());

            //TEST BLOCK {
            //classJava javaFileTest = new classJava();
            //file.readClass("testBlock.java", javaFileTest);
            //javaFileTest.show(); //Come�a em zero ARRAY!!!
            //searchContext(data.getSatdCollection().get(i), 82, javaFileTest);
            //System.out.println(data.getSatdCollection().get(i).show());
            //for(int t = 0; t < data.getSatdCollection().get(i).getContextInstances().size(); t++) {
            //	System.out.println(data.getSatdCollection().get(i).getContextInstances().get(t).show());
            //}

            //TEST BLOCK }
            //classJava javaFileTest = new classJava();
            //file.readClass("testBlock.java", javaFileTest);
            //javaFileTest.show(); //Come�a em zero ARRAY!!!
            //searchContext(data.getSatdCollection().get(i), 132, javaFileTest);
            //System.out.println(data.getSatdCollection().get(i).show());
            //for(int t = 0; t < data.getSatdCollection().get(i).getContextInstances().size(); t++) {
            //	System.out.println(data.getSatdCollection().get(i).getContextInstances().get(t).show());
            //}

            //break;
            //System.out.println("\n\n");

        }
    }

    public static String getPathSATD(String path) {

        String elements[], pathLocation = "";
        elements = path.split("/");

        for(int i = 0; i < elements.length; i++) {

            if((i+1) == elements.length) {
                pathLocation += elements[i];
            }
            else pathLocation += elements[i] + "\\";
        }
        return pathLocation;
    }

    public static void searchContext(Satd satd, int finalLineSATD, ClassJava javaFile) {

        boolean identified = false;

        identified = contextIsClass(satd, finalLineSATD, javaFile);
        if(identified == true) return;

        identified = contextIsMethod(satd, finalLineSATD, javaFile);
        if(identified == true) return;

        identified = contextIsBlock(satd, finalLineSATD, javaFile);
        if(identified == true) return;

    }

    public static boolean contextIsClass(Satd satd, int finalLineSATD, ClassJava javaFile) {

        String line = "none";

        for(int i = finalLineSATD; i < javaFile.getLines().size(); i++) {

            line = javaFile.getLines().get(i);

            if(line.contains("public class") || line.contains("private class") || line.contains("protected class")) {

                int j = i, counterLeft = 0, counterRight = 0;
                for(; j < javaFile.getLines().size(); j++) {

                    line = javaFile.getLines().get(j);

                    if(line.contains("{") && line.contains("}")) continue;
                    else if(line.contains("{")) counterLeft++;
                    else if(line.contains("}")) counterRight++;
                    else if( (counterLeft == counterRight) && (counterLeft != 0) ) break;
                }

                Context c;
                if( (counterLeft == counterRight) && (counterLeft != 0) ) {
                    c = new Context("Class", 1, i+"", j+"");
                }
                else {
                    c = new Context("Class", 1, i+"", javaFile.getLines().size()+"");
                }
                satd.getContextInstances().add(c);
                return true;
            }
            //else if(javaFile.getLines().get(i).isEmpty()) continue;
            else if(line.contains("{") || line.contains("}")) return false;

			/*line = javaFile.getLines().get(i);

			if(line.contains("public class") || line.contains("private class") || line.contains("protected class")) {

				context c = new context("Class", 1, i+"", javaFile.getLines().size()+"");
				//ARRUMAR AQUI CONTAR { E } IGUAIS TERMINA E FINAL LINHA!!!
				satd.getContextInstances().add(c);
				return true;
			}
			//else if(javaFile.getLines().get(i).isEmpty()) continue;
			//else return false;
			*/
        }
        return false;

    }

    public static boolean contextIsMethod(Satd satd, int finalLineSATD, ClassJava javaFile) {

        int counterParenLeft = 0;
        int counterParenRight = 0;
        int initialLineMethod = 0;
        int finalLineMethod = 0;
        String line;
        boolean isMethod = false;

        for(int i = finalLineSATD; i < javaFile.getLines().size(); i++) {

            line = javaFile.getLines().get(i);

            if(isMethod == false) {

                if( (line.contains("public") || line.contains("protected") || line.contains("private")) && line.contains("(") && line.contains(")") ) {

                    isMethod = true;
                    initialLineMethod = i+1;
                    if(line.contains("{")) counterParenLeft++;
                    continue;
                }
                else if(javaFile.getLines().get(i).isEmpty()) continue;
                else return false;

            }
            else {

                if(line.contains("{")) counterParenLeft++;
                if(line.contains("}")) counterParenRight++;
            }

            if( (counterParenLeft == counterParenRight) && counterParenLeft != 0 ) {
                finalLineMethod = i+1;
                break;
            }

        } //end for

        String lineInitial = initialLineMethod + "";
        String lineFinal = finalLineMethod + "";

        Context c = new Context("Method", 1, lineInitial, lineFinal);
        satd.getContextInstances().add(c);
        return true;

    }

    public static boolean contextIsBlock(Satd satd, int finalLineSATD, ClassJava javaFile) {

        HashMap<Integer,ArrayList<Context>> map = new HashMap<Integer,ArrayList<Context>>();

        String line, type, verif = "None";
        ArrayList<Context> contextList = new ArrayList<>();
        int currentContext = 0;
        int counterParenLeft = 0;
        int counterParenRight = 0;
        int initialLineBlock, finalLineBlock = 0;

        for(int i = finalLineSATD; i < javaFile.getLines().size(); i++) {

            line = javaFile.getLines().get(i);

            if(line.contains("{") && line.contains("}")) continue;
            else if(line.contains("{")) {
                verif = "{";
                break;
            }
            else if(line.contains("}")){
                verif = "}";
                counterParenRight++;
                currentContext++;
                finalLineBlock = i+1;
                break;
            }

        }

        if(verif.equals("{")) {

            for(int i = finalLineSATD; i < javaFile.getLines().size(); i++) {

                type = "None";
                line = javaFile.getLines().get(i);
                //System.out.println(line);
                if(line.contains("{") && line.contains("}")) continue;
                if(line.contains("{")) {

                    counterParenLeft++;

                    if(line.contains("for") || line.contains("while") || line.contains("do")) type = "Loop";
                    else if(line.contains("if") || line.contains("else") || line.contains("else if")) type = "Selection";

                    currentContext++;
                    initialLineBlock = i+1;

                    Context c = new Context(type, currentContext, String.valueOf(initialLineBlock), "NONE");

                    if(map.size() < currentContext) {
                        ArrayList<Context> list = new ArrayList<>();
                        list.add(c);
                        map.put(currentContext, list);
                    }
                    else {
                        map.get(currentContext).add(c);
                    }

                }
                else if(line.contains("}")) {

                    counterParenRight++;
                    finalLineBlock = i+1;
                    //System.out.println(finalLineBlock);
                    //System.out.println(finalLineBlock + " - " + currentContext + " - " + counterParenLeft + " - " + counterParenRight);
                    for(int j = 0; j < map.get(currentContext).size(); j++) {

                        if(map.get(currentContext).get(j).getLineFinal().equals("NONE")) {

                            map.get(currentContext).get(j).setLineFinal(String.valueOf(finalLineBlock));
                        }

                    }//end internal for

                    currentContext--;

                }

                if( (counterParenLeft == counterParenRight) && (counterParenLeft != 0) ) break;
                else continue;

            }//end external for

        }//end if verif {
        else {

            Context c = new Context("None", currentContext, "NONE", String.valueOf(finalLineBlock));
            ArrayList<Context> list = new ArrayList<>();
            list.add(c);
            map.put(currentContext, list);

            int i = finalLineSATD - 1;
            while(counterParenLeft != counterParenRight) {

                type = "None";
                line = javaFile.getLines().get(i);

                if(line.contains("{") && line.contains("}")) {
                    //System.out.println(line);
                    i--;
                    continue;
                }

                if(line.contains("}")) {

                    counterParenRight++;

                    //if(line.contains("for") || line.contains("while") || line.contains("do")) type = "Loop";
                    //else if(line.contains("if") || line.contains("else") || line.contains("else if")) type = "Selection";

                    currentContext++;
                    finalLineBlock = i+1;

                    c = new Context("None", currentContext, "NONE", String.valueOf(finalLineBlock));

                    if(map.size() < currentContext) {
                        list = new ArrayList<>();
                        list.add(c);
                        map.put(currentContext, list);
                    }
                    else {
                        map.get(currentContext).add(c);
                    }

                }
                else if(line.contains("{")) {

                    counterParenLeft++;
                    initialLineBlock = i+1;

                    for(int j = 0; j < map.get(currentContext).size(); j++) {

                        if(map.get(currentContext).get(j).getLineInitial().equals("NONE")) {

                            map.get(currentContext).get(j).setLineInitial(String.valueOf(initialLineBlock));
                        }

                    }//end internal for

                    currentContext--;

                }

                i--;

            }//end while

        }//end else verif }

        Set<Integer> keys = map.keySet();
        for (Integer key : keys){

            for(int j = 0; j < map.get(key).size(); j++) {

                contextList.add(map.get(key).get(j));
            }
        }

        satd.setContextInstances(contextList);

        return true;

    }

}
