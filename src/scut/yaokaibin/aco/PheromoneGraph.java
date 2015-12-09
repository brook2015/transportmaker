package scut.yaokaibin.aco;

import scut.yaokaibin.demand.DemandPool;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by YaoKaibin on 2015/11/22.
 */
public class PheromoneGraph {
    private int edgeNum;
    // key: index of origin location
    private Map<Integer, Vector<PheromoneEdge>> graph;
    private static final BigDecimal rho = new BigDecimal(0.7);
    private static final BigDecimal pheromone = new BigDecimal(0.01);

    public PheromoneGraph() {
        this.edgeNum = 0;
        this.graph = new HashMap<>();
    }

    public PheromoneGraph(DemandPool pool) {
        this();
        if (pool == null) throw new NullPointerException("Demand pool is null.");
        Iterable<Integer> vertex = pool.getVertex();
        for (int o : vertex) {
            for (int d : vertex) {
                if (o != d) addEdge(o, d);
            }
        }
    }

    public int getEdgeNum() {
        return edgeNum;
    }

    public void addEdge(int orig, int dest) {
        PheromoneEdge edge = new PheromoneEdge(orig, dest, pheromone);
        addEdge(edge);
    }

    private void addEdge(PheromoneEdge edge) {
        int orig = edge.getOrig();
        if (!graph.containsKey(orig)) graph.put(orig, new Vector<>());
        graph.get(orig).add(edge);
        edgeNum++;
    }

    // get pheromoneAccumulate by index of origin and destination
    public double getPheromone(int orig, int dest) {
        PheromoneEdge edge = getEdge(orig, dest);
        return edge == null ? -1 : edge.getPheromone().doubleValue();
    }

    public void addPheromone(int orig, int dest, double value) {
        PheromoneEdge edge = getEdge(orig, dest);
        if (edge != null) {
            edge.setPheromone(edge.getPheromone().add(new BigDecimal(value)));
        }
    }

    private PheromoneEdge getEdge(int orig, int dest) {
        for (Map.Entry<Integer, Vector<PheromoneEdge>> entry : graph.entrySet()) {
            if (orig == entry.getKey()) {
                for (PheromoneEdge edge : entry.getValue()) {
                    if (dest == edge.getDest())
                        return edge;
                }
            }
        }
        return null;
    }

    public void reset() {
        graph.entrySet().forEach(g -> g.getValue().forEach(e -> e.setPheromone(pheromone)));
    }

    @Override
    public String toString() {
        String result = "";
        for (Map.Entry<Integer, Vector<PheromoneEdge>> entry : graph.entrySet()) {
            for (PheromoneEdge edge : entry.getValue()) {
                //if (0.01<pheromone.doubleValue()) {
                    result += edge + ";";
                //}
            }
        }
        return result;
    }

    public void volatilize(){
        for (Map.Entry<Integer, Vector<PheromoneEdge>> entry : graph.entrySet()) {
            entry.getValue().forEach(e->e.setPheromone(e.getPheromone().multiply(rho)));
        }
    }
}
