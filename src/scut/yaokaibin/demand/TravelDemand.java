package scut.yaokaibin.demand;


import scut.yaokaibin.aco.Record;

/**
 * Created by YaoKaibin on 2015/11/22.
 */
public class TravelDemand extends DemandBase {
    protected boolean serve;
    protected int index;
    protected int orig;
    protected int dest;
    protected int travellerNum;
    protected int departure;
    private double distance;

    public TravelDemand(int orig, int dest, int travellerNum, int departure) {
        this.orig = orig;
        this.dest = dest;
        this.travellerNum = travellerNum;
        this.departure = departure;
        serve = false;
        index = indexFactory.getIndex();
        distance = allPairsSP.dist(orig, dest);
    }

    public int getDeparture() {
        return departure;
    }

    public void initiate(Record record) {
        int travelTime = DemandBase.getTravelTime(distance);
        record.add(orig, travellerNum, 0, departure, true);
        record.add(dest, 0, travellerNum, departure + travelTime, false);
        record.addDistance(distance);
        record.addTravellerNum(travellerNum);
    }

    public int getIndex() {
        return index;
    }

    public int getOrig() {
        return orig;
    }

    public double getDistance() {
        return distance;
    }

    public int getDest() {
        return dest;
    }

    public int getTravellerNum() {
        return travellerNum;
    }

    public int departure() {
        return departure;
    }

    public boolean serve() {
        return serve;
    }

    public void setServe(boolean serve) {
        this.serve = serve;
    }

    @Override
    public String toString() {
        return String.format("%d %d->%d %d @%d %s", index, orig, dest, travellerNum, departure, serve);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TravelDemand) {
            TravelDemand td = (TravelDemand) obj;
            if (index == td.getIndex()) return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return index;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            TravelDemand d = new TravelDemand(1, 3, 5, 7);
            System.out.println(d);
        }
    }
}
