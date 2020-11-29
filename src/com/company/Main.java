
package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.StringTokenizer;
//1) класс должен заниматься одним конкретным делом, то есть если уж он у тебя занимается расчетами,
//   то не должен принимать ввод юзера, пусть ему параметры передают в конструктор или метод.
//   А он пусть возвращает значение, а не пишет
//2) весь ввод выносим в один класс, сканнер должен быть как горец - только 1
public class Main {
    static double deltaT, density, temperature, rCut, velMag, timeNow, uSum, vvSum, virSum;
    static double dispHi, rNebrShell;
    static int nebrTabFac, nebrTabLen, nebrTabMax;
//    static int nebrNow;
    static boolean nebrNow;
    static int stepAvg, stepEquil, stepLimit, nMol, stepCount;
//    static int stepAvg, stepEquil, stepLimit, nMol, stepCount;
    static final int N_OFFSET = 14;
    static int stepInitlzTemp, randSeed;

    public static void main(String[] args) throws IOException {
        int[][] OFFSET_VALLS = {{0, 0, 0}, {1, 0, 0}, {1, 1, 0}, {0, 1, 0}, {-1, 1, 0},
                {0, 0, 1}, {1, 0, 1}, {1, 1, 1}, {0, 1, 1}, {-1, 1, 1},
                {-1, 0, 1}, {-1, -1, 1}, {0, -1, 1}, {1, -1, 1}};

//        ArrayList<VecI> offset_vals = new ArrayList<>();

//        for (int i = 0; i < N_OFFSET; i ++) {
//            offset_vals.add(new VecI());
//            for (int z = 0; z < 3; z ++) {
//                if (z == 0)
//                    offset_vals.get(i).x = OFFSET_VALLS[i][z];
//                else if (z == 1)
//                    offset_vals.get(i).y = OFFSET_VALLS[i][z];
//                else if (z == 2)
//                    offset_vals.get(i).z = OFFSET_VALLS[i][z];
//            }
//        }
        final int NDIM = 3;
        boolean moreCycles;

        double kinEnInitSum;
        double pertTrajDev;
        int countTrajDev, limitTrajDev, stepTrajDev;
        VecR vSum = new VecR();
        Prop kinEnergy = new Prop();
        Prop totEnergy = new Prop();
        Prop pressure = new Prop();

//        File inFile = new File("/home/dmint/Desktop/pr_03_5.in");
        File inFile = new File("pr_03_2.in");
        File outFile1 = new File("coords.d");
        File outFile2 = new File("gpcoords.d");
        File outFile3 = new File("velo.d");
        File outFile4 = new File("veloAvg.d");
        File outFile5 = new File("coordsStep1.d");
        File outFile6 = new File("veloStep1.d");
        File outFile7 = new File("cellList.d");
        File outFile8 = new File("results.d");


        BufferedReader in = new BufferedReader(new FileReader(inFile));
        PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(outFile1))); //jmol coords.d
        PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter(outFile2))); //gpcoords.d
        PrintWriter out3 = new PrintWriter(new BufferedWriter(new FileWriter(outFile3))); //gnuplot
        PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(outFile4))); //gnuplot
        PrintWriter out5 = new PrintWriter(new BufferedWriter(new FileWriter(outFile5))); //gnuplot
        PrintWriter out6 = new PrintWriter(new BufferedWriter(new FileWriter(outFile6))); //gnuplot
        PrintWriter out7 = new PrintWriter(new BufferedWriter(new FileWriter(outFile7))); //gnuplot
        PrintWriter out8 = new PrintWriter(new BufferedWriter(new FileWriter(outFile8))); //gnuplot results.d

        ArrayList<NameI> nameI = new ArrayList<>();
        ArrayList<NameR> nameR = new ArrayList<>();
        

        ArrayList<Double> valTrajDev = new ArrayList<>();
        ArrayList<Mol> mol = new ArrayList<>();

        InitUcell initUcell = new InitUcell();

        VeloSum veloSum = new VeloSum();
        CalcMet calcMet = new CalcMet();
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
                case "randSeed":
                    randSeed = nameI.get(i).getvValue();
                    break;
            }
        }
        System.out.println("==================================");
        System.out.println("stepEquil = " + stepEquil);
//  SetParams()
        rCut = Math.pow(2., 1./6.);
        System.out.println("rCut = " + rCut);
//        Region region = new Region(density, initUcell, "sc");
//        Region region = new Region(density, initUcell, "bcc");
//        Region region = new Region(density, initUcell, "fcc");
        Region region = new Region(density, initUcell, "diamond");
//        region.setnMol();
        nMol = region.nMol;
        System.out.println("structName = " + region.structName);
        System.out.printf("region = %f %f %f\n", region.x, region.y, region.z);
        velMag = Math.sqrt(NDIM * (1./nMol) * temperature); //        System.out.println("velMag = " + velMag);
        Cells cells = new Cells(rCut, rNebrShell, region);
        System.out.printf("cells = %d %d %d\n", cells.x, cells.y, cells.z);
        nebrTabMax = nebrTabFac * nMol;

//  SetUpJob(AllocArraya, InitRand, InitCoords, InitVels, InitAccels, AccumProps)

        SetParams setParams = new SetParams();

        int sizeCellList = setParams.VProdI(cells) + nMol;
//        System.out.println("sizeCellList = " + sizeCellList);
        int sizeNebrTab = 2 * nebrTabMax;
//        ArrayList<Integer> cellList = new ArrayList<Integer>();
        int[] cellList = new int[sizeCellList];
//        System.out.println("size of cellList = " + cellList.size());
//        ArrayList<Integer> nebrTab = new ArrayList<>();
        int[] nebrTab = new int[sizeNebrTab];
        InitVels initVels = new InitVels();
        initVels.initRand(randSeed);
        stepCount = 0;
        Coords coords = new Coords();

//        System.out.printf("gap = %f %f %f\n", coords.gapX, coords.gapY, coords.gapZ);
        out1.printf("%s\nC\n", Integer.toString(nMol)); // jmol coords.d
//        System.out.println("Hello from coords!");
//        coords.setStructure();
//        System.out.printf("coords = %f %f %f\n", coords.x, coords.y, coords.z);
        if (region.structName.equals("sc")) {
            coords.setGap(region, initUcell);
            int n = 0;
            for (int nz = 0; nz < initUcell.z; nz++) {
                for (int ny = 0; ny < initUcell.y; ny++) {
                    for (int nx = 0; nx < initUcell.x; nx++) {
                        coords.setInitCoords(nx + 0.5, ny + 0.5, nz + 0.5);
                        coords.setCoordsGap();
                        coords.setCoordsRegion(-0.5, region);
                        mol.add(new Mol());
                        mol.get(n).r.x = coords.x;
                        mol.get(n).r.z = coords.z;
                        mol.get(n).r.y = coords.y;
//                    System.out.printf("%s %f %f %f\n", 'C', mol.get(n).r.x, mol.get(n).r.y, mol.get(n).r.z);
                        out1.printf("%s %f %f %f\n", 'C', mol.get(n).r.x, mol.get(n).r.y, mol.get(n).r.z); // jmol
                        out2.printf("%f %f %f\n", mol.get(n).r.x, mol.get(n).r.y, mol.get(n).r.z); // gnuplot
                        n++;
                    }
                }
            }
        }
        else if (region.structName.equals("bcc")) {
            coords.setGap(region, initUcell);
            int n = 0;
            for (int nz = 0; nz < initUcell.z; nz++) {
                for (int ny = 0; ny < initUcell.y; ny++) {
                    for (int nx = 0; nx < initUcell.x; nx++) {
                        coords.setInitCoords(nx + 0.25, ny + 0.25, nz + 0.25);
                        coords.setCoordsGap();
                        coords.setCoordsRegion(-0.5, region);
                        for (int j = 0; j < 2; j ++) {
                            mol.add(new Mol());
                            mol.get(n).r.x = coords.x;
                            mol.get(n).r.z = coords.z;
                            mol.get(n).r.y = coords.y;
                            if (j == 1) {
                                mol.get(n).r.x += 0.5 + coords.gapX;
                                mol.get(n).r.z += 0.5 + coords.gapY;
                                mol.get(n).r.y += 0.5 + coords.gapZ;
                            }
                            out1.printf("%s %f %f %f\n", 'C', mol.get(n).r.x, mol.get(n).r.y, mol.get(n).r.z); // jmol
                            out2.printf("%f %f %f\n", mol.get(n).r.x, mol.get(n).r.y, mol.get(n).r.z); // gnuplot
                            n++;
                        }
                    }
                }
            }
        }
        else if (region.structName.equals("fcc")) {
            coords.setGap(region, initUcell);
            int n = 0;
            for (int nz = 0; nz < initUcell.z; nz++) {
                for (int ny = 0; ny < initUcell.y; ny++) {
                    for (int nx = 0; nx < initUcell.x; nx++) {
                        coords.setInitCoords(nx + 0.25, ny + 0.25, nz + 0.25);
                        coords.setCoordsGap();
                        coords.setCoordsRegion(-0.5, region);
                        for (int j = 0; j < 4; j ++) {
                            mol.add(new Mol());
                            mol.get(n).r.x = coords.x;
                            mol.get(n).r.z = coords.z;
                            mol.get(n).r.y = coords.y;
                            if (j != 3) {
                                if (j != 0)
                                    mol.get(n).r.x += 0.5 * coords.gapX;
                                if (j != 1)
                                    mol.get(n).r.y += 0.5 * coords.gapY;
                                if (j != 2)
                                    mol.get(n).r.z += 0.5 * coords.gapZ;
                            }
                            out1.printf("%s %f %f %f\n", 'C', mol.get(n).r.x, mol.get(n).r.y, mol.get(n).r.z); // jmol
                            out2.printf("%f %f %f\n", mol.get(n).r.x, mol.get(n).r.y, mol.get(n).r.z); // gnuplot
                            n++;
                        }
                    }
                }
            }
        }
        else if (region.structName.equals("diamond")) {
            double subShift;
            coords.setGap(region, initUcell);
            int n = 0;
            for (int nz = 0; nz < initUcell.z; nz++) {
                for (int ny = 0; ny < initUcell.y; ny++) {
                    for (int nx = 0; nx < initUcell.x; nx++) {
                        coords.setInitCoords(nx + 0.125, ny + 0.125, nz + 0.125);
                        coords.setCoordsGap();
                        coords.setCoordsRegion(-0.5, region);
                        for (int m = 0; m < 2; m ++) {
                            subShift = (m == 1) ? 0.25 : 0.;
                            for (int j = 0; j < 4; j ++) {
                                mol.add(new Mol());
                                mol.get(n).r.x = coords.x + subShift * coords.gapX;
                                mol.get(n).r.y = coords.y + subShift * coords.gapY;
                                mol.get(n).r.z = coords.z + subShift * coords.gapZ;
                                if (j != 3) {
                                    if (j != 0)
                                        mol.get(n).r.x += 0.5 * coords.gapX;
                                    if (j != 1)
                                        mol.get(n).r.y += 0.5 * coords.gapY;
                                    if (j != 2)
                                        mol.get(n).r.z += 0.5 * coords.gapZ;
                                }
                                out1.printf("%s %f %f %f\n", 'C', mol.get(n).r.x, mol.get(n).r.y, mol.get(n).r.z); // jmol
                                out2.printf("%f %f %f\n", mol.get(n).r.x, mol.get(n).r.y, mol.get(n).r.z); // gnuplot
                                n++;
                            }
                        }
                    }
                }
            }
        }
        else
            System.out.println("Error! Wrong Structure!");
//        SetUpJobs InitVels()

        veloSum.setZeroR();
        System.out.printf("nMol = %d\n", nMol);
        for (int i = 0; i < nMol; i ++) {
//            mol.add(new Mol());
            initVels.vRand();
            initVels.setVScale(velMag);
            mol.get(i).rv.x = initVels.x;
            mol.get(i).rv.y = initVels.y;
            mol.get(i).rv.z = initVels.z;
            out3.printf("%f %f %f\n", mol.get(i).rv.x, mol.get(i).rv.y, mol.get(i).rv.z); //velo.d
//            out3.printf("%d %f %f %f\n", i + 1, mol.get(i).rv.x, mol.get(i).rv.y, mol.get(i).rv.z);
            veloSum.setVAdd(mol.get(i).rv);
        }

        System.out.printf("veloSum = %f %f %f\n", veloSum.x, veloSum.y, veloSum.z);
        for (int i = 0; i < mol.size(); i ++) {
            mol.get(i).setAvgVel(- 1. / nMol, veloSum);
            out4.printf("%f %f %f\n", mol.get(i).rv.x, mol.get(i).rv.y, mol.get(i).rv.z); //veloAvg.d
        }
//        InitAccels()
        for (int i = 0; i < mol.size(); i ++) {
            mol.get(i).setZeroRA();
//            System.out.printf("mol.ra = %f %f %f\n", mol.get(i).ra.x, mol.get(i).ra.y, mol.get(i).ra.z);
        }

// AccumProps(0)
        totEnergy.propZero();
        kinEnergy.propZero();
        pressure.propZero();
        kinEnInitSum = 0.;
        nebrNow = true; // AccumProps(0)
//============= End of SetUpJobs() =========================
        
        ApplyBC pbc = new ApplyBC();
        CellWrapAll cellWrapAll = new CellWrapAll();

//        System.out.printf("mol size = %f %f %f\n", mol.get(0).r.x, mol.get(0).rv.x, mol.get(0).ra.x);
// Begin SingleStep==================================================================================
        moreCycles = true;
        while (moreCycles) {
            ++stepCount;
            timeNow = stepCount * deltaT;
//            LeapFrogMethod(part 1)///////////////////////////////////////////////////////////////////
            for (int i = 0; i < mol.size(); i++) {
//                mol.add(new Mol());
//                calcMet.leapFrogStep(0.5 * deltaT, mol.get(i).ra);
                setParams.VSAdd(mol.get(i).rv, 0.5 * deltaT, mol.get(i).ra);
                mol.get(i).rv = setParams.rv;
//                mol.get(i).rv.x = setParams.rv.x;
//                mol.get(i).rv.y = setParams.rv.y;
//                mol.get(i).rv.z = setParams.rv.z;
                setParams.VVSAdd(mol.get(i).r, deltaT, mol.get(i).rv);
                mol.get(i).r = setParams.r;
//                mol.get(0).r.x = -9.;
//                System.out.printf("mol[0].r.x = " + mol.get(0).r.x);
            }// End of LeapFrog(part 1) Method////////////////////////////////////////////////////////////

            for (int i = 0; i < mol.size(); i++) { //Apply Boundary Conditions////////////////
                pbc.setBCtoAll(mol.get(i).r, region);
                mol.get(i).r = pbc.pr;
            } // End of Boundary Conditions////////////////
//            Biuld Neibor List ///////////////////////////////////////////////////////
            if (nebrNow) {
                nebrNow = false;
                dispHi = 0.;
                VecR dr, invWid, rs, shift;
                VecI cc, m1v, m2v;
                setParams.VSet(0, 0, 0);
                m2v = setParams.d;
//                int[][] vOff = OFFSET_VALLS;
                double rrNebr;
                int c, m1, m2, offset;
                rrNebr = (rCut + rNebrShell) * (rCut + rNebrShell);
//                System.out.println("rrNebr = " + rrNebr);
                setParams.VDiv(cells, region);
                invWid = setParams.r;
                System.out.println("invWid = " + invWid);
                for (int n = nMol; n < nMol + setParams.VProdI(cells); n++) {
                    cellList[n] = -1;
//                    out7.printf("%d\n", cellList[n]/*, cellList[c]*/); // cellList.d
                }
                for (int n = 0; n < nMol; n++) {
                    setParams.addRegion(mol.get(n).r, 0.5, region);
                    rs = setParams.r;
                    setParams.VMulI(rs, invWid);
                    cc = setParams.d;
                    c = setParams.setLinear(cc, cells) + nMol;
                    cellList[n] = cellList[c];
                    cellList[c] = n;
//                    out7.printf("cellList N = %d\t cellList C = %d\n", cellList[n], cellList[c]); // cellList.d
                }
                nebrTabLen = 0;
                for (int m1z = 0; m1z < cells.z; m1z++) {
                    for (int m1y = 0; m1y < cells.y; m1y++) {
                        for (int m1x = 0; m1x < cells.x; m1x++) {
                            setParams.VSet(m1x, m1y, m1z);
                            m1v = setParams.d;
//                            System.out.printf("m1v = %d %d %d\n", m1v.x, m1v.y, m1v.z);
//                            out7.printf("m1v = %d %d %d\n", m1v.x, m1v.y, m1v.z); // cellList.d
//                            break;
                            m1 = setParams.setLinear(m1v, cells) + nMol;
//                            out7.printf("m1 = %d\n", m1); // cellList.d
                            for (offset = 0; offset < N_OFFSET; offset++) {
                                for (int w = 0; w < 3; w++) {
                                    if (w == 0)
                                        m2v.x = m1v.x + OFFSET_VALLS[offset][w];
                                    else if (w == 1)
                                        m2v.y = m1v.y + OFFSET_VALLS[offset][w];
                                    else if (w == 2)
                                        m2v.z = m1v.z + OFFSET_VALLS[offset][w];
                                }
//                                System.out.printf("m2v = %d %d %d\n", m2v.x, m2v.y, m2v.z);
//                                out7.printf("m2v = %d %d %d\n", m1v.x, m1v.y, m1v.z); // cellList.d
                                setParams.setZeroR();
                                shift = setParams.r;
                                if (m2v.x >= cells.x) {
                                    m2v.x = 0;
                                    shift.x = region.x;
                                } else if (m2v.x < 0) {
                                    m2v.x = cells.x;
                                    shift.x = -region.x;
                                }
                                if (m2v.y >= cells.y) {
                                    m2v.y = 0;
                                    shift.y = region.y;
                                }
//                                out7.printf("m2v = %d %d %d\tshift = %f %f %f\n", m2v.x, m2v.y, m2v.z,
//                                        shift.x, shift.y, shift.z); // cellList.d
                                m2 = setParams.setLinear(m2v, cells) + nMol;
//                                out7.printf("m2 = %d\n", m2); // cellList.d
                                for (int j1 = cellList[m1]; j1 >= 0; j1 = cellList[j1]) {
                                    for (int j2 = cellList[m2]; j2 >= 0; j2 = cellList[j2]) {
                                        if (m1 != m2 || j2 < j1) {
                                            setParams.VSub(mol.get(j1).r, mol.get(j2).r);
                                            dr = setParams.r;
                                            setParams.VVSub(shift);
                                            dr = setParams.r;
//                                            System.out.printf("dr = %f %f %f\n", dr.x, dr.y, dr.z);
                                            double dx = setParams.vLenSq(dr);
                                            System.out.println("dx = " + dx);
                                            if (dx < rrNebr) {
                                                if (nebrTabLen >= nebrTabMax)
                                                    System.out.println("ERROR TOO MANY MEMBERS!!!");
                                                nebrTab[2 * nebrTabLen] = j1;
                                                nebrTab[2 * nebrTabLen + 1] = j2;
                                                System.out.printf("%d nebrTab[j1] = %d\tnebrTab[j2] = %d\n",
                                                        nebrTabLen, nebrTab[2 * nebrTabLen], nebrTab[2 * nebrTabLen + 1]);
                                                ++nebrTabLen;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } // End of Buil Nebor List////////////////////////////////////////////
//  Compute Force()////////////////////////////////////////////////////////////////////
            VecR dr;
            double fcVal, rr, rrCut, rri, rri3, uVal;
            int j1, j2;

            rrCut = rCut * rCut;
            for (int i = 0; i < nMol; i++)
                mol.get(i).setZeroRA();
            uSum = 0.;
            virSum = 0.;
            for (int n = 0; n < nebrTabLen; n++) {
                j1 = nebrTab[2 * n];
                j2 = nebrTab[2 * n + 1];
                setParams.VSub(mol.get(j1).r, mol.get(j2).r);
                dr = setParams.r;
                pbc.setBCtoAll(dr, region);
                dr = pbc.pr;
                rr = setParams.vLenSq(dr);
                if (rr < rrCut) {
                    rri = 1. / rr;
                    rri3 = setParams.cube(rri);
                    fcVal = 48. * rri3 * (rri3 - 0.5) * rri;
                    uVal = 4. * rri3 * (rri3 - 1.) + 1.;
                    setParams.VVSAdd(mol.get(j1).ra, fcVal, dr);
                    mol.get(j1).ra = setParams.rv;
                    setParams.VVSAdd(mol.get(j2).ra, -fcVal, dr);
                    mol.get(j2).ra = setParams.rv;
                    uSum += uVal;
                    virSum += fcVal * rr;
                }
            }// End of ComputeForce()=========================================
//            LeapFrogMethod(part 2)///////////////////////////////////////////////////////////////////
            for (int i = 0; i < mol.size(); i++) {
                setParams.VSAdd(mol.get(i).rv, 0.5 * deltaT, mol.get(i).ra);
                mol.get(i).rv = setParams.rv;
            }// End of LeapFrog(part 2) Method////////////////////////////////////////////////////////////
//            EvalProps()=====================================================================
            double vv, vvMax;
            setParams.setZeroR();
            vSum = setParams.r;
            vvSum = 0.;
            vvMax = 0.;
            for (int n = 0; n < nMol; n++) {
                setParams.VAdd(mol.get(n).rv);
                vSum = setParams.rv;
                vv = setParams.vLenSq(mol.get(n).rv);
                vvSum += vv;
                vvMax = Math.max(vvMax, vv);
            }
            dispHi += Math.sqrt(vvMax) * deltaT;
            if (dispHi > 0.5 * rNebrShell)
                nebrNow = true;
            kinEnergy.val = 0.5 * vvSum / nMol;
            totEnergy.val = kinEnergy.val + uSum / nMol;
            pressure.val = density * (vvSum + virSum) / (nMol * NDIM);
//            End of EvalProps() method================================================
            if (stepCount < stepEquil) {//AdjustInitTemp()=====================================================
                double vFac;
                kinEnInitSum += kinEnergy.val;
                if (stepCount % stepInitlzTemp == 0) {
                    kinEnInitSum /= stepInitlzTemp;
                    vFac = velMag / Math.sqrt(2. * kinEnInitSum);
                    for (int n = 0; n < nMol; n++) {
                        initVels.setVScale(vFac);
                        mol.get(n).rv.x = initVels.x;
                        mol.get(n).rv.y = initVels.y;
                        mol.get(n).rv.z = initVels.z;
                    }
                    kinEnInitSum = 0.;
                }
            }// End AdjustInitTemp()=============================================================================
            totEnergy.propAccum(); //Begin AccumProps(1)
            kinEnergy.propAccum();
            pressure.propAccum(); //End AccumProps(1)
            if (stepCount % stepAvg == 0) {
                totEnergy.propAvg(stepAvg);//Begin AccumProps(2)
                kinEnergy.propAvg(stepAvg);
                pressure.propAvg(stepAvg); //End AccumPropsAvg(2)
//                PrintSummary();
                System.out.printf("%5d %8.4f %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f\n",
                        stepCount, timeNow, setParams.VCSum(vSum) / nMol, totEnergy.sum, totEnergy.sum2,
                        kinEnergy.sum, kinEnergy.sum2, pressure.sum, pressure.sum2);
                out8.printf("%5d %8.4f %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f\n",
                        stepCount, timeNow, setParams.VCSum(vSum) / nMol, totEnergy.sum, totEnergy.sum2,
                        kinEnergy.sum, kinEnergy.sum2, pressure.sum, pressure.sum2); //results.d
                out8.flush();
//                End PrintSummary()
                totEnergy.propZero(); // AccumProps(0)
                kinEnergy.propZero();
                pressure.propZero(); // AccumProps(0)
            }
//            if (stepCount >= stepLimit) {
                moreCycles = false;
//            }
        }
//                  System.out.printf("mol.rv = %f %f %f\n", mol.get(i).rv.x, mol.get(i).rv.y, mol.get(i).rv.z);
//                setParams.VVSAdd(mol.get(i).r, deltaT, mol.get(i).rv);
//                calcMet.leapFrogStep(deltaT, mol.get(i).rv);
//                mol.get(i).r.x = setParams.r.x;
//                mol.get(i).r.y = setParams.r.y;
//                mol.get(i).r.z = setParams.r.z;
//                mol.get(i).r.x = calcMet.x;
//                mol.get(i).r.y = calcMet.y;
//                mol.get(i).r.z = calcMet.z;

//                System.out.printf("%d %f %f %f\t%f %f %f\n", i, mol.get(i).r.x, mol.get(i).r.y, mol.get(i).r.z,
//                        mol.get(i).rv.x, mol.get(i).rv.y, mol.get(i).rv.z);
//                if (stepCount == 100) {
//                    out5.printf("%s %f %f %f\n", 'C', mol.get(i).r.x, mol.get(i).r.y, mol.get(i).r.z); // coordsStep1.d
//                    out6.printf("%f %f %f\n", mol.get(i).rv.x, mol.get(i).rv.y, mol.get(i).rv.z); //veloSteps1.d
//                }

//            if (stepCount == 100)

//        }
//        System.out.printf("totEnergy %f %f\n", totEnergy.sum, totEnergy.sum2);
//        System.out.printf("kinEnergy %f %f\n", kinEnergy.sum, kinEnergy.sum2);
//        System.out.printf("pressure %f %f\n", pressure.sum, pressure.sum2);
        out1.close(); //jmol coords.d
        out2.close(); //gnuplot gcoords.d
        out3.close(); //gnuplot velocityScale.d
        out4.close(); //gnuplot
        out5.close();
        out6.close();
        out7.close();
        out8.close();
//        System.out.println("nMol = " + nMol);
    }
}