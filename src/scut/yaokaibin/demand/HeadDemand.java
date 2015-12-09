package scut.yaokaibin.demand;


import scut.yaokaibin.aco.PheromoneGraph;
import scut.yaokaibin.aco.Record;

/**
 * Created by yaokaibin on 15-12-1.
 */
public class HeadDemand extends DemandBase implements DemandInterface {
    protected double distance;
    private DemandType type;
    protected Record record;
    protected TravelDemand demand;
    private PheromoneGraph graph;

    public HeadDemand(TravelDemand demand, Record record, PheromoneGraph graph) {
        this.demand = demand;
        this.record = record;
        this.graph = graph;
        type = DemandType.HeadDemand;
        calcDistance();
    }

    public DemandType getType() {
        return type;
    }

    private void calcDistance() {
        distance = allPairsSP.dist(
                demand.getOrig(),
                record.getHeadId()
        );
    }

    public boolean overload() {
        return record.overload(
                record.getHeadId(),
                demand.getDest(),
                demand.getTravellerNum()
        );
    }

    public boolean timeAllow() {
        return timeAllow(
                demand.getDeparture(),
                record.getHeadTime() - getTravelTime(distance)
        );
    }

    public boolean profit() {
        int totalTravellerNum = record.getTotalTravellerNum() + demand.getTravellerNum();
        double totalDistance = record.getTotalDistance() + distance;
        return price.profitable(totalTravellerNum, totalDistance);
    }

    public double calcProbability() {
        double pheromone = getPheromone();
        int travellerNum = demand.getTravellerNum();
        return probability(pheromone, travellerNum, distance);
    }

    public boolean allowedLength() {
        return record.getTotalDistance() + distance <= LENGTH_LIMIT;
    }

    private double getPheromone() {
        return graph.getPheromone(demand.getOrig(), demand.getDest());
    }

    @Override
    public void moveForward() {
        int travellerNum = demand.getTravellerNum();
        int time = record.getHeadTime() - getTravelTime(distance);
        record.addDistance(distance);
        record.addTravellerNum(travellerNum);
        record.add(demand.getOrig(), travellerNum, 0, time, true);
        record.addOff(demand.getDest(), travellerNum);
    }

    @Override
    public boolean validate() {
        return !overload() && timeAllow() && profit() && allowedLength();
    }

    @Override
    public void removeDemand(DemandPool pool) {
        pool.mark(demand.getIndex());
    }
}
