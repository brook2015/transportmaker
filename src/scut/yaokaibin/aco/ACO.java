package scut.yaokaibin.aco;

import scut.yaokaibin.demand.DemandPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Created by YaoKaibin on 2015/11/22.
 */
public class ACO {
    private int groupNum;
    private int generationNum;
    private int antNumPerGroup;
    private double bestProfit;
    private double bestRatio;
    private Record[] records;
    private AntGroup[] groups;
    //private AntGroup bestGroup;
    //private static Scanner scanner;

    public ACO(int groupNum, int generationNum, int antNumPerGroup) {
        this.groupNum = groupNum;
        this.generationNum = generationNum;
        this.antNumPerGroup = antNumPerGroup;
        records = new Record[antNumPerGroup];
        initAntGroups();
    }

    /*static {
        try {
            scanner = new Scanner(new File("demands.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    public Record[] getRecords() {
        return records;
    }

    public double getBestRatio(){
        return bestRatio;
    }

    public double getBestProfit(){
        return bestProfit;
    }

    public void initAntGroups() {
        groups = new AntGroup[groupNum];
        for (int i = 0; i < groupNum; i++) {
            try {
                Scanner scanner = new Scanner(new File("demands.txt"));
                DemandPool pool = new DemandPool(scanner);
                groups[i] = new AntGroup(antNumPerGroup, pool);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void iterate() {
        for (int i = 0; i < generationNum; i++) {
            for (AntGroup group : groups) {
                group.initiate();
                group.iterate1();
            }
            optimize1();
        }
    }

    public void optimize() {
        for (AntGroup group : groups) {
            double profit = group.getTotalProfit();
            if (bestProfit < profit) {
                bestProfit = profit;
                records = group.getRecords();
            }
        }
    }

    public void optimize1(){
        for (AntGroup group : groups) {
            double ratio = group.getRatio();
            if (bestRatio < ratio) {
                bestRatio = ratio;
                bestProfit = group.getTotalProfit();
                //bestGroup = group;
                //System.out.println("best ratio: " + bestRatio);
                records = group.getRecords();
            }
        }
    }
}