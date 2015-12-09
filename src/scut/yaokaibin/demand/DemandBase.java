package scut.yaokaibin.demand;

import scut.yaokaibin.aco.Price;
import scut.yaokaibin.network.DijkstraAllPairsSP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by yaokaibin on 15-12-2.
 */
public class DemandBase implements DemandInterface {
    public static final int LIMIT = 45;
    public static final double SPEED = 35.0;
    public static final int TIME_GAP = 30;
    public static final double LENGTH_LIMIT = 40;
    protected static Price price;
    protected static DijkstraAllPairsSP allPairsSP;
    protected static IndexFactory indexFactory;

    static {
        try {
            File file = new File("od.txt");
            allPairsSP = new DijkstraAllPairsSP(new Scanner(file));
            indexFactory = new IndexFactory();
            price = Price.generalPrice();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int getTravelTime(double distance) {
        return (int) Math.floor(60 * distance / SPEED);
    }

    protected static boolean timeAllow(int time1, int time2) {
        return Math.abs(time1 - time2) <= TIME_GAP;
    }

    protected boolean profit() {
        return true;
    }

    protected boolean overload() {
        return true;
    }

    protected boolean timeAllow() {
        return true;
    }

    @Override
    public double calcProbability() {
        return 0;
    }

    @Override
    public void moveForward() {
    }

    public static double probability(double p, int t, double d) {
        return Math.pow(p, 0.2) * Math.pow(t, 2) / Math.pow(d, 2);
    }

    public boolean validate() {
        return !overload() && timeAllow() && profit();
    }

    @Override
    public void removeDemand(DemandPool pool) {

    }
}
