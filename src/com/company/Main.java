package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    static VecI cells = new VecI();
    static VecR region = new VecR();
    static int nebrNow, nebrTabFac, nebrTabLen, nebrTabMax;
    static VecR gap = new VecR();
    static ArrayList<Mol> mol = new ArrayList<>();


    public static void main(String[] args) throws IOException {


        final int NDIM = 3;

        int stepAvg, stepEquil, stepLimit, nMol, moreCycles, stepCount;

        double kinEnInitSum;
        double pertTrajDev;
        int stepInitlzTemp;
        int countTrajDev, limitTrajDev, stepTrajDev;
        Prop kinEnergy = new Prop();
        Prop totEnergy = new Prop();
        File pr = new File("/home/dmint/Desktop/pr_03_5.in");
        BufferedReader in = new BufferedReader(new FileReader(pr));
        ArrayList<NameI> nameI = new ArrayList<>();
        ArrayList<NameR> nameR = new ArrayList<>();
        ArrayList<Integer> cellList = new ArrayList<>();
        ArrayList<Integer> nebrTab = new ArrayList<>();
        ArrayList<Double> valTrajDev = new ArrayList<>();
//        VecI initUcell = new VecI();
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
                        if (x.contains(".") || y.contains("."))
                            throw new InputMismatchException("Parameters of initUcell must be integers!");
                        initUcell.x = Integer.parseInt(x);
                        initUcell.y = Integer.parseInt(y);
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
                        initUcell.x = Integer.parseInt(x);
                        initUcell.y = Integer.parseInt(y);
                        initUcell.z = Integer.parseInt(z);
                    }
                    else
                        throw new InputMismatchException("Too many data! In line " + countLine);
                    break;
                case 5:
                    throw new InputMismatchException("Too many data! In line " + countLine);
            }
            if (countToken == 2) {
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
//        System.out.println("density = " + density);
        rCut = Math.pow(2., 1./6.);
        Region region = new Region(1./Math.pow(density / 4.,1./3.), initUcell);
//        SetParams setParams = new SetParams();
//        setParams.VSCopyR(region, 1./Math.pow(density / 4.,1./3.), );
        System.out.printf("region is %f %f %f\n", region.x, region.y, region.z);
        nMol = 8 * initUcell.volume();
//        nMol = 8 * setParams.VProd(initUcell);
//        velMag = Math.sqrt(NDIM * (1./nMol) * temperature);
//        setParams.VSCopyI(cells, 1./(rCut + rNebrShell), region);
        System.out.printf("Cells are %d %d %d\n", cells.x, cells.y, cells.z);
//        nebrTabMax = nebrTabFac * nMol;
//        System.out.println("velMag = " + velMag);
        System.out.println("nMol = " + nMol);
        System.out.println("nebrTabMax = " + nebrTabMax);
//        System.out.println("vsCopy = " + vsCopy);

//        setParams.VDiv(gap, region, initUcell);
//        System.out.printf("Gaps are %f %f %f\n", gap.x, gap.y, gap.z);

        VecR rs = new VecR();
//        ArrayList<NameR> nameR = new ArrayList<>();
//        nameR.add(new NameR(description, Double.parseDouble(value), flag));
        mol.add(new Mol());
        mol.get(0).setR(1., 2., 3.);
//        System.out.println("mol is " + mol.get(0));
//        setParams.VSet(mol.add(new Mol().r), 1, 2, 3);
        System.out.printf("Positions of 1st mol is %f %f %f\n", mol.get(0).r.x, mol.get(0).r.y, mol.get(0).r.z);
    }
//    VSCopy vsCopy = new VSCopy();
//    VProd vProd = new VProd();
//    int nMol;

}

