package scut.yaokaibin.demand;

import scut.yaokaibin.aco.Record;

/**
 * Created by yaokaibin on 15-12-1.
 */
/*public class DecoratedDemand extends TravelDemand {
    private int type;
    private double distance;
    private Record record;
    private double moveDistance;
    private boolean overload;

    public DecoratedDemand(int orig, int dest, int travellerNum, int departure) {
        super(orig, dest, travellerNum, departure);
        type=-1;
        record=null;
    }

    public void initiate(Record record){
        this.record=record;
        setType();
        calcDistance();
    }

    private void calcDistance() {
        if (type == 0) {
            int headId = record.getHeadId();
            distance = allPairsSP.dist(orig, headId);
        } else if (type == 1) {
            distance = 0;
        } else if (type == 2) {
            int tailId = record.getTailId();
            distance = allPairsSP.dist(tailId, dest);
        } else if (type == 4) {
            distance = Double.MAX_VALUE;
        }
    }

    public double calcProbability(){
        return 0.0;
    }

    private void setType(){

    }

    protected boolean overload(){
        return true;
    }
}*/
