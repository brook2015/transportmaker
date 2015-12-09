package scut.yaokaibin.entry;

import scut.yaokaibin.aco.Ant;
import scut.yaokaibin.aco.PheromoneGraph;
import scut.yaokaibin.aco.Record;
import scut.yaokaibin.demand.DemandInterface;
import scut.yaokaibin.demand.DemandPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by YaoKaibin on 2015/11/26.
 */
public class Program {
    public static void main(String[] args) {
        try {
            File file = new File("demands.txt");
            Scanner scanner = new Scanner(file);
            DemandPool pool = new DemandPool(scanner);
            PheromoneGraph graph = new PheromoneGraph(pool);
            Ant ant = new Ant(graph);

            double ratio = 0.0;
            Record record = null;
            for (int i = 0; i < 100; i++) {
                ant.initiate(pool);
                while (ant.isKeepGoing()) ant.moveForward(pool);
                double temp = ant.getRatio();
                if (ratio < temp) {
                    ratio = temp;
                    record = ant.getRecord();
                    System.out.println("dist: " + record.getTotalDistance());
                    System.out.println("count: " + record.getTotalTravellerNum());
                    System.out.println("profit: " + ant.getProfit());
                    System.out.println("ratio: " + ratio);
                    System.out.println("--");
                }
                ant.pheromoneUpdate();
                pool.recover();
            }
            System.out.println(record);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}