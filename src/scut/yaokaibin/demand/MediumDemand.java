package scut.yaokaibin.demand;

import scut.yaokaibin.aco.PheromoneGraph;
import scut.yaokaibin.aco.Record;

/**
 * Created by yaokaibin on 15-12-1.
 */
public class MediumDemand extends DemandBase implements DemandInterface {
    protected double distance;
    private DemandType type;
    protected Record record;
    protected TravelDemand demand;
    private PheromoneGraph graph;

    public MediumDemand(TravelDemand demand, Record record, PheromoneGraph graph) {
        this.demand = demand;
        this.record = record;
        this.graph = graph;
        type = DemandType.MediumDemand;
        calcDistance();
    }

    public DemandType getType() {
        return type;
    }

    public void initiate(Record record) {
        this.record = record;
        //calcDistance();
    }

    private void calcDistance() {
        //distance = allPairsSP.dist(demand.getOrig(), record.getHeadId());
        distance = 0.001;
    }

    public boolean overload() {
        return record.overload(
                demand.getOrig(),
                demand.getDest(),
                demand.getTravellerNum()
        );
    }

    public boolean allowedLength() {
        return record.getTotalDistance() + distance <= LENGTH_LIMIT;
    }

    public boolean timeAllow() {
        return timeAllow(
                record.getTime(demand.getOrig()),
                demand.getDeparture()
        );
    }

    public boolean profit() {
        int totalTravellerNum = record.getTotalTravellerNum() + demand.getTravellerNum();
        double totalDistance = record.getTotalDistance();
        return price.profitable(totalTravellerNum, totalDistance);
    }

    @Override
    public double calcProbability() {
        double pheromone = getPheromone();
        int travellerNum = demand.getTravellerNum();
        //return probability(pheromone, travellerNum, distance);
        double p = probability(pheromone, travellerNum, distance);
        if (Double.isInfinite(p)) {
            System.out.println(demand);
        }
        return p;
    }

    private double getPheromone() {
        return graph.getPheromone(demand.getOrig(), demand.getDest());
    }

    @Override
    public void moveForward() {
        int travellerNum = demand.getTravellerNum();
        record.addTravellerNum(travellerNum);
        record.addOn(demand.getOrig(), travellerNum);
        record.addOff(demand.getDest(), travellerNum);
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
