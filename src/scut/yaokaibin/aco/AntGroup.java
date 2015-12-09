package scut.yaokaibin.aco;

import scut.yaokaibin.demand.DemandPool;

/**
 * Created by YaoKaibin on 2015/11/22.
 */
public class AntGroup {
    private int antNum;
    private Ant[] ants;
    private Record[] records;
    private DemandPool demands;
    private double bestProfit;
    private double bestRatio;

    public AntGroup(int antNum, DemandPool demands) {
        this.bestRatio = 0.0;
        this.antNum = antNum;
        this.demands = demands;
        records = new Record[antNum];
        ants = new Ant[antNum];
        PheromoneGraph graph = new PheromoneGraph(demands);
        for (int i = 0; i < antNum; i++) {
            ants[i] = new Ant(graph);
        }
    }

    public Record[] getRecords() {
        return records;
    }

    public int getAntNum() {
        return antNum;
    }

    public void initiate() {
        demands.recover();
        for (Ant ant : ants) ant.initiate(demands);
    }

    public void iterate() {
        while (!isAllStop()) {
            for (Ant ant : ants) ant.moveForward(demands);
            double profit = getTotalProfit();
            if (profit > bestProfit) {
                bestProfit = profit;
                storeRecord();
            }
        }
        updatePheromone();
    }

    public void iterate1() {
        while (!isAllStop()) {
            for (Ant ant : ants) ant.moveForward(demands);
        }
        double ratio = getRatio();
        if (ratio > bestRatio) {
            bestRatio = ratio;
            storeRecord();
        }
        updatePheromone();
    }

    public double getTotalProfit() {
        double total = 0.0;
        for (Ant ant : ants) {
            total += ant.getProfit();
        }
        return total;
    }

    public void storeRecord() {
        for (int i = 0; i < antNum; i++) {
            records[i] = ants[i].getRecord();
        }
    }

    public boolean isAllStop() {
        for (Ant ant : ants) {
            if (ant.isKeepGoing()) return false;
        }
        return true;
    }

    public void updatePheromone() {
        for (Ant ant : ants) ant.pheromoneUpdate();
    }

    public double getRatio() {
        int totalOn = 0;
        for (Ant ant : ants) totalOn += ant.getRecord().getTotalTravellerNum();
        int totalTraveller = demands.getTotalTraveller();
        return ((double) totalOn) / ((double) totalTraveller);
    }
}
