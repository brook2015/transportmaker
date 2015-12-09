package scut.yaokaibin.aco;

import scut.yaokaibin.demand.TravelDemand;

import java.math.BigDecimal;

/**
 * Created by YaoKaibin on 2015/11/22.
 */
public class PheromoneEdge {
    private int orig; // index of origin location
    private int dest; // index of destination location
    private BigDecimal pheromone;

    @Override
    public String toString() {
        return String.format("%d->%d %.5f", orig, dest, pheromone);
    }

    public PheromoneEdge(int orig, int dest, BigDecimal pheromone) {
        this.orig = orig;
        this.dest = dest;
        this.pheromone = pheromone;
    }

    public int getOrig() {
        return orig;
    }

    public int getDest() {
        return dest;
    }

    public BigDecimal getPheromone() {
        return pheromone;
    }

    public void setPheromone(BigDecimal pheromone) {
        this.pheromone = pheromone;
    }
}
