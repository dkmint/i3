package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.InputMismatchException;
import java.util.StringTokenizer;

public class Io2 {
    public static void main(String[] args) throws IOException {
        File pr = new File("/home/dmint/csi/pr3/pr_03_1.in");
        BufferedReader in = new BufferedReader(new FileReader(pr));
        Double deltaT, density, temperature;
        Integer stepAvg, stepEquil, stepInitlzTemp, stepLimit;
        ArrayList<Double> param = new ArrayList<Double>();
        ArrayList<Integer> steps = new ArrayList<Integer>();
        ArrayList<String> descriptionName = new ArrayList<String>();
        ArrayList<NameI> nameI = new ArrayList<>();
        ArrayList<NameR> nameR = new ArrayList<>();
        ArrayList<UCell> uCells = new ArrayList<>();
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
                        descriptionName.add(description);
                    }
                    else
                        descriptionName.add(description);
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
                        descriptionName.add(description);
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
                        descriptionName.add(description);
                    }
                    else
                        throw new InputMismatchException("Too many data! In line " + countLine);
                    break;
                case 5:
                    throw new InputMismatchException("Too many data! In line " + countLine);
            }
            if (countToken == 2) {
                System.out.println(description + " " + value);
                if (flag == 1)
                    param.add(Double.parseDouble(value));
                else
                    steps.add(Integer.parseInt(value));
            }
            else if (countToken == 3)
                System.out.println(description + " " + x + " " + y);
            else if (countToken == 4)
                System.out.println(description + " " + x + " " + y + " " + z);
            countLine ++;
            flag = 0;
        }
        in.close();
        for (int i = 0; i < descriptionName.size(); i ++) {
            if (descriptionName.get(i).equals("deltaT"))
                deltaT = param.get(i);
            System.out.println(descriptionName.get(i));
        }

    }
}