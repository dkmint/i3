package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.InputMismatchException;
import java.util.StringTokenizer;
//1) класс должен заниматься одним конкретным делом, то есть если уж он у тебя занимается расчетами,
//   то не должен принимать ввод юзера, пусть ему параметры передают в конструктор или метод.
//   А он пусть возвращает значение, а не пишет
//2) весь ввод выносим в один класс, сканнер должен быть как горец - только 1
public class Main {
    static double deltaT, density, temperature, rCut, velMag, timeNow, uSum, vvSum;
    static double dispHi, rNebrShell;
    static int nebrNow, nebrTabFac, nebrTabLen, nebrTabMax;

    public static void main(String[] args) throws IOException {

        final int NDIM = 3;

        int stepAvg, stepEquil, stepLimit, nMol, moreCycles, stepCount;
        double kinEnInitSum;
        double pertTrajDev;
        int stepInitlzTemp;
        int countTrajDev, limitTrajDev, stepTrajDev;
        Prop kinEnergy = new Prop();
        Prop totEnergy = new Prop();
        File inFile = new File("/home/dmint/Desktop/pr_03_5.in");
        File outFile1 = new File("coords.d");
        File outFile2 = new File("randSeedP.d");
        PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(outFile1)));
        PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter(outFile2)));
        BufferedReader in = new BufferedReader(new FileReader(inFile));
        ArrayList<NameI> nameI = new ArrayList<>();
        ArrayList<NameR> nameR = new ArrayList<>();
        ArrayList<Integer> cellList = new ArrayList<>();
        ArrayList<Integer> nebrTab = new ArrayList<>();
        ArrayList<Double> valTrajDev = new ArrayList<>();
        ArrayList<Mol> mol = new ArrayList<>();
        InitUcell initUcell = new InitUcell();

        VecR vSum = new VecR();
        String line;
        String value = "";
        String description = "";
        String x = "", y = "", z = "";
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
                    if (value.contains(".") || value.contains("e-")) {
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
                        if (x.contains(".") || y.contains(".")) {
                            throw new InputMismatchException("Parameters of initUcell must be integers!");
                        }
                        initUcell.x = Integer.parseInt(x);
                        initUcell.y = Integer.parseInt(y);
                    }
                    else {
                        throw new InputMismatchException("Too many data! In line " + countLine);
                    }
                    break;
                case 4:
                    description = stLine.nextToken();
                    if (description.equals("initUcell")) {
                        while (stLine.hasMoreTokens()) {
                            x = stLine.nextToken();
                            y = stLine.nextToken();
                            z = stLine.nextToken();
                        }
                        if (x.contains(".") || y.contains(".") || z.contains(".")) {
                            throw new InputMismatchException("Parameters of initUcell must be integers!");
                        }
                        initUcell.x = Integer.parseInt(x);
                        initUcell.y = Integer.parseInt(y);
                        initUcell.z = Integer.parseInt(z);
                    }
                    else {
                        throw new InputMismatchException("Too many data! In line " + countLine);
                    }
                    break;
                case 5:
                    throw new InputMismatchException("Too many data! In line " + countLine);
            }
            if (countToken == 2) {
                System.out.println(description + "\t\t" + value);
            }
            else if (countToken == 3) {
                System.out.println(description + "\t" + x + " " + y);
            }
            else if (countToken == 4) {
                System.out.println(description + "\t" + x + " " + y + " " + z);
            }
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
                case "pertTrajDev":
                    pertTrajDev = nameR.get(i).getvValue();
                    break;
                case "rNebrShell":
                    rNebrShell = nameR.get(i).getvValue();
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
                case "limitTrajDev":
                    limitTrajDev = nameI.get(i).getvValue();
                    break;
                case "nebrTabFac":
                    nebrTabFac = nameI.get(i).getvValue();
                    break;
                case "stepTrajDev":
                    stepTrajDev = nameI.get(i).getvValue();
                    break;
            }
        }
        System.out.println("==================================");
//  SetParams()
        rCut = Math.pow(2., 1./6.);
        Region region = new Region(density, initUcell);
        nMol = 8 * initUcell.volume();
        velMag = Math.sqrt(NDIM * (1./nMol) * temperature);
        Cells cells = new Cells(rCut, rNebrShell, region);
        nebrTabMax = nebrTabFac * nMol;

//  SetUpJob(InitCoords)
        stepCount = 0;
        Gap gap = new Gap(region, initUcell);
        Coords coords = new Coords();
        int nn = nMol / 8;
        out1.printf("%s\nC\n", Integer.toString(nn));
        int n = 0;
        for (int nz = 0; nz < initUcell.z; nz ++) {
            for (int ny = 0; ny < initUcell.y; ny ++) {
                for (int nx = 0; nx < initUcell.x; nx ++) {
                    coords.setInitCoords(nx + 0.5, ny + 0.5, nz + 0.5);
                    coords.setCoordsGap(gap);
                    coords.setCoordsRegion(-0.5, region);
                    mol.add(new Mol());
                    mol.get(n).r.x = coords.x;
                    mol.get(n).r.z = coords.z;
                    mol.get(n).r.y = coords.y;
//                    System.out.printf("%s %f %f %f\n", 'C', mol.get(n).r.x, mol.get(n).r.y, mol.get(n).r.z);
//                    out.printf("%s %f %f %f\n", 'C', mol.get(n).r.x, mol.get(n).r.y, mol.get(n).r.z);
                    n ++;
                }
            }
        }
        InitVels initVels = new InitVels();

        for (int i = 0; i < nMol; i ++) {
            mol.add(new Mol());
            initVels.vRand();
            mol.get(i).rv.x = initVels.x;
            mol.get(i).rv.y = initVels.y;
            mol.get(i).rv.z = initVels.z;
            System.out.printf("%d %f %f %f\n", i + 1, mol.get(i).rv.x, mol.get(i).rv.y, mol.get(i).rv.z);
            out2.printf("%d %f %f %f\n", i + 1, mol.get(i).rv.x, mol.get(i).rv.y, mol.get(i).rv.z);
        }
        out1.close();
        out2.close();
        System.out.println("nMol = " + nMol);
    }
}

