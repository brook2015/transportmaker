package scut.yaokaibin.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by yaokaibin on 15-11-29.
 */
public class DijkstraAllPairsSP {
    private int count;
    private Vector<ShortestEdge> edges;

    public DijkstraAllPairsSP() {
        count = 0;
        edges = new Vector<>();
    }

    public DijkstraAllPairsSP(Scanner scanner) {
        this();
        int c = scanner.nextInt();
        if (c < 0) throw new IllegalArgumentException("Count of od is invalid.");
        for (int i = 0; i < c; i++) {
            int orig = scanner.nextInt();
            int dest = scanner.nextInt();
            double distance = scanner.nextDouble();
            double time = scanner.nextDouble();
            ShortestEdge edge = new ShortestEdge(orig, dest, distance, time);
            add(edge);
        }
    }

    public void add(ShortestEdge edge) {
        edges.add(edge);
        count++;
    }

    public void add(Collection<ShortestEdge> edges) {
        this.edges.addAll(edges);
        count += edges.size();
    }

    public int getCount() {
        return count;
    }

    public double dist(int orig, int dest) {
        Optional<ShortestEdge> shortestEdge = edges.stream().filter(
                edge -> orig == edge.getOrig() && dest == edge.getDest()).findFirst();
        if (shortestEdge.isPresent()) {
            return shortestEdge.get().getDistance() * 0.75;
        }
        return -1;
    }

    public static void main(String[] args) {
        try {
            File file = new File("od.txt");
            Scanner scanner = new Scanner(file);
            DijkstraAllPairsSP allPairsSP = new DijkstraAllPairsSP(scanner);
            System.out.println(allPairsSP.dist(20, 11));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
