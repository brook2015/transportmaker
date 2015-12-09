package scut.yaokaibin.demand;

import scut.yaokaibin.aco.PheromoneGraph;
import scut.yaokaibin.aco.Record;

/**
 * Created by yaokaibin on 15-12-1.
 */
public class TailDemand extends DemandBase implements DemandInterface {
    protected double distance;
    private DemandType type;
    protected Record record;
    protected TravelDemand demand;
    private PheromoneGraph graph;

    public TailDemand(TravelDemand demand, Record record, PheromoneGraph graph) {
        this.demand = demand;
        this.record = record;
        this.graph = graph;
        type = DemandType.TailDemand;
        calcDistance();
    }

    public DemandType getType() {
        return type;
    }

    private void calcDistance() {
        //distance = allPairsSP.dist(record.getHeadId(), demand.getOrig());
        distance = allPairsSP.dist(record.getTailId(),demand.getDest());
    }

    public boolean overload() {
        return record.overload(
                demand.getOrig(),
                record.getTailId(),
                demand.getTravellerNum()
        );
    }

    public boolean timeAllow() {
        return timeAllow(
                record.getTime(demand.getOrig()),
                demand.getDeparture()
        );
    }

    public boolean profit() {
        int totalTravellerNum = record.getTotalTravellerNum() + demand.getTravellerNum();
        double totalDistance = record.getTotalDistance() + distance;
        return price.profitable(totalTravellerNum, totalDistance);
    }

    public boolean allowedLength() {
        return record.getTotalDistance() + distance <= LENGTH_LIMIT;
    }

    @Override
    public double calcProbability() {
        double pheromone = graph.getPheromone(demand.getOrig(), demand.getDest());
        int travellerNum = demand.getTravellerNum();
        //return probability(pheromone, travellerNum, distance);
        double p = probability(pheromone, travellerNum, distance);
        if (Double.isInfinite(p)) {
            System.out.println(demand);
        }
        return p;
    }

    @Override
    public void moveForward() {
        int travellerNum = demand.getTravellerNum();
        int time = record.getTailTime() + getTravelTime(distance);
        record.addTravellerNum(travellerNum);
        record.addDistance(distance);
        record.add(demand.getDest(), 0, travellerNum, time, false);
        record.addOn(demand.getOrig(), travellerNum);
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