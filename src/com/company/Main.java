package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.InputMismatchException;
import java.util.StringTokenizer;

public class Main {
    public static Double deltaT, density, temperature, rCut;
    public static Integer stepAvg, stepEquil, stepInitlzTemp, stepLimit;
    public static void main(String[] args) throws IOException {
        File pr = new File("/home/dmint/Desktop/pr_02_1.in");
        BufferedReader in = new BufferedReader(new FileReader(pr));
        ArrayList<Double> param = new ArrayList<Double>();
        ArrayList<Integer> steps = new ArrayList<Integer>();
        ArrayList<String> descriptionName = new ArrayList<String>();
        ArrayList<NameI> nameI = new ArrayList<>();
        ArrayList<NameR> nameR = new ArrayList<>();
        UCell uCells = new UCell();
        String line;
        String value = null;
        String description = null;
        String x = null, y = null, z = null;
        int countLine = 1;
        int countToken = 0;
        int flag = 0;
        while ((line = in.readLine()) != null) {
            line = line.trim();
            StringTokenizer stLine = new StringTokenizer(line, " ");
            countToken = stLine.countTokens();
            switch (countToken) {
                case 1:
                    throw new InputMismatchException("Incopatible data!!! In line " + countLine);
                case 2:
                    description = stLine.nextToken();
                    if (description.equals("initUcell"))
                        throw new InputMismatchException("Few data! In line " + countLine);
                    value = stLine.nextToken();
                    if (value.contains(".")) {
                        flag = 1;
                        nameR.add(new NameR(description, Double.parseDouble(value), flag));
                    }
                    else {
                        nameI.add(new NameI(description, Integer.parseInt(value)));
                    }
                    break;
                case 3:
                    description = stLine.nextToken();
                    if (description.equals("initUcell")) {
                        while (stLine.hasMoreTokens()) {
                            x = stLine.nextToken();
                            y = stLine.nextToken();
                        }
                        if (x.contains(".") || y.contains("."))
                            throw new InputMismatchException("Parameters of initUcell must be integers!");
                        uCells.setName(description);
                        uCells.setX(Integer.parseInt(x));
                        uCells.setY(Integer.parseInt(y));
                        uCells.setSize(2);
                    }
                    else
                        throw new InputMismatchException("Too many data! In line " + countLine);
                    break;
                case 4:
                    description = stLine.nextToken();
                    if (description.equals("initUcell")) {
                        while (stLine.hasMoreTokens()) {
                            x = stLine.nextToken();
                            y = stLine.nextToken();
                            z = stLine.nextToken();
                        }
                        if (x.contains(".") || y.contains(".") || z.contains("."))
                            throw new InputMismatchException("Parameters of initUcell must be integers!");
                        uCells.setName(description);
                        uCells.setX(Integer.parseInt(x));
                        uCells.setY(Integer.parseInt(y));
                        uCells.setZ(Integer.parseInt(x));
                        uCells.setSize(3);
                    }
                    else
                        throw new InputMismatchException("Too many data! In line " + countLine);
                    break;
                case 5:
                    throw new InputMismatchException("Too many data! In line " + countLine);
            }
            if (countToken == 2 && flag == 1) {
                System.out.println(description + "\t\t" + value);
            }
            else if (countToken == 3)
                System.out.println(description + "\t" + x + " " + y);
            else if (countToken == 4)
                System.out.println(description + "\t" + x + " " + y + " " + z);
            countLine ++;
            flag = 0;
        }
        in.close();
        for (int i = 0; i < nameR.size(); i ++) {
            switch (nameR.get(i).getvName()) {
                case "deltaT":
                    deltaT = nameR.get(i).getvValue();
                    break;
                case "density":
                    density = nameR.get(i).getvValue();
                    break;
                case "temperature":
                    temperature = nameR.get(i).getvValue();
                    break;
            }
        }
        for (int i = 0; i < nameI.size(); i ++) {
            switch (nameI.get(i).getvName()) {
                case "stepAvg":
                    stepAvg = nameI.get(i).getvValue();
                    break;
                case "stepEquil":
                    stepEquil = nameI.get(i).getvValue();
                    break;
                case "stepInitlzTemp":
                    stepInitlzTemp = nameI.get(i).getvValue();
                    break;
                case "stepLimit" :
                    stepLimit = nameI.get(i).getvValue();
                    break;
            }
        }
        double rCut = 0.;
        SetParams setParams = new SetParams();
        rCut = Math.pow(2., 1./6.);
        System.out.println("rCut = " + rCut);
        VecR region = new VecR();
        System.out.println(region.x = 5.);
        VSCopy vsCopy = new VSCopy();
        vsCopy.VSCopy(region, 1./Math.sqrt(density), uCells);
        System.out.println(region.x + " " + region.y + " " + region.z);
        Mol mol = new Mol();
        mol.r.x = 3.4;
        System.out.println(mol.r.x);
    }
}