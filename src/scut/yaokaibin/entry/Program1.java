package scut.yaokaibin.entry;

import scut.yaokaibin.aco.ACO;
import scut.yaokaibin.aco.Record;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by yaokaibin on 15-12-8.
 */
public class Program1 {
    public static void main(String[] args) {
        try {
            int groupNum = 50;
            int generationNum = 3;
            int antNumPerGroup = 5;
            ACO aco = new ACO(groupNum, generationNum, antNumPerGroup);
            aco.iterate();
            Record[] records = aco.getRecords();

            int totalTraveller = 0;
            double totalDistance = 0.0;
            for (Record record : records) {
                totalDistance += record.getTotalDistance();
                totalTraveller += record.getTotalTravellerNum();
                System.out.println(record);
            }
            System.out.println(String.format("count: %d", totalTraveller));
            System.out.println(String.format("distance: %.4f", totalDistance));
            System.out.println("profit: " + aco.getBestProfit());
            System.out.println("ratio: " + aco.getBestRatio());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
