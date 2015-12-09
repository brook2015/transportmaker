package scut.yaokaibin.demand;

import scut.yaokaibin.aco.PheromoneGraph;
import scut.yaokaibin.aco.Record;

/**
 * Created by yaokaibin on 15-12-2.
 */
public class IndirectDemand extends DemandBase implements DemandInterface {
    protected double distance;
    private DemandType type;
    protected Record record;
    protected TravelDemand demand;
    private PheromoneGraph graph;

    public IndirectDemand(TravelDemand demand, Record record, PheromoneGraph graph) {
        this.demand = demand;
        this.record = record;
        this.graph = graph;
        type = DemandType.IndirectDemand;
        calcDistance();
    }

    public DemandType getType() {
        return type;
    }

    @Override
    public double calcProbability() {
        double pheromone = graph.getPheromone(demand.getOrig(), demand.getDest());
        //return probability(pheromone, 1, distance);
        double p = probability(pheromone, 1, distance);
        if (Double.isInfinite(p)) {
            System.out.println(demand);
        }
        return p;
    }

    private void calcDistance() {
        distance = allPairsSP.dist(
                record.getTailId(),
                demand.getOrig()
        );
    }

    public boolean allowedLength() {
        return record.getTotalDistance() + distance <= LENGTH_LIMIT;
    }

    public boolean overload() {
        return demand.getTravellerNum() > LIMIT;
    }

    public boolean profit() {
        int totalTravellerNum = record.getTotalTravellerNum();
        double totalDistance = record.getTotalDistance() + distance;
        return price.profitable(totalTravellerNum, totalDistance);
    }

    @Override
    public void moveForward() {
        int time = record.getTailTime() + getTravelTime(distance);
        record.addDistance(distance);
        record.add(demand.getOrig(), 0, 0, time, false);
    }

    @Override
    public void removeDemand(DemandPool pool) {
        pool.mark(demand.getIndex());
    }

    @Override
    public boolean validate() {
        return !overload() && timeAllow() && profit() && allowedLength();
    }
}
