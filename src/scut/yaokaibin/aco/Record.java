package scut.yaokaibin.aco;

import scut.yaokaibin.demand.DemandBase;
import scut.yaokaibin.demand.DemandType;
import scut.yaokaibin.demand.TravelDemand;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Created by YaoKaibin on 2015/11/25.
 */
public class Record {
    private double totalDistance;
    private int totalTravellerNum;
    private static final int LIMIT = 45;
    private int count;
    private Vector<OnOff> records;
    private static final Price price = Price.generalPrice();

    public Record() {
        count = 0;
        records = new Vector<>();
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public int getTotalTravellerNum() {
        return totalTravellerNum;
    }

    public void add(int id, int on, int off, int time, boolean headOrTail) {
        OnOff oo = new OnOff(id, on, off, time);
        if (headOrTail) records.add(0, oo);
        else records.add(oo);
        count++;
    }

    public double profit() {
        return price.profit(totalTravellerNum, totalDistance);
    }

    public int getHeadId() {
        OnOff first = records.firstElement();
        if (first == null) throw new NullPointerException("first is null");
        return first.getId();
    }

    public int getTailId() {
        return records.lastElement().getId();
    }

    public int getHeadTime() {
        OnOff first = records.firstElement();
        if (first == null) throw new NullPointerException("first is null");
        return records.firstElement().getTime();
    }

    public int getTailTime() {
        return records.lastElement().getTime();
    }

    public void addOn(int id, int on) {
        OnOff oo = get(id);
        if (oo == null) throw new IllegalArgumentException("Id is invalid");
        oo.addOn(on);
    }

    public void addOff(int id, int off) {
        OnOff oo = get(id);
        if (oo == null) throw new IllegalArgumentException("Id is invalid");
        oo.addOff(off);
    }

    private OnOff get(int id) {
        for (OnOff record : records) if (id == record.getId()) return record;
        return null;
    }

    public int totalOn() {
        int total = 0;
        for (OnOff record : records) {
            total += record.getOn();
        }
        return total;
    }

    public void addDistance(double addition) {
        totalDistance += addition;
    }

    public void addTravellerNum(double addition) {
        totalTravellerNum += addition;
    }

    public boolean pass(int id) {
        int size = records.size();
        if (size <= 1) return false;
        for (int i = 0; i < size - 1; i++) {
            if (id == records.get(i).getId()) return true;
        }
        return false;
    }

    public int getTime(int id) {
        OnOff oo = get(id);
        if (oo == null) throw new NullPointerException("null");
        return oo.getTime();
    }

    public boolean overload(int orig, int dest, int travellerNum) {
        int total = 0;
        boolean isTarget = false;
        for (OnOff record : records) {
            total += record.getDelta();
            if (orig == record.getId()) isTarget = true;
            if (isTarget && dest == record.getId()) return false;
            if (isTarget && total + travellerNum > LIMIT) return true;
        }
        return false;
    }

    public Map<Integer, Integer> getOn() {
        int total = 0;
        Map<Integer, Integer> on = new HashMap<>(records.size());
        for (OnOff record : records) {
            int id = record.getId();
            total += record.getDelta();
            on.put(id, total);
        }
        return on;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        String result = "";
        for (OnOff oo : records) {
            result += oo.toString() + ";";
        }
        return result;
    }

    public Iterator<Integer> getSequence() {
        Vector<Integer> ids = new Vector<>(records.size());
        ids.addAll(records.stream().map(OnOff::getId).collect(Collectors.toList()));
        return ids.iterator();
    }

    public DemandType type(TravelDemand demand) {
        int orig = demand.getOrig();
        int dest = demand.getDest();
        int current = getTailId();
        if (pass(orig)) {
            if (dest == current || isMediumDemand(orig, dest))
                return DemandType.MediumDemand;
            else return DemandType.TailDemand;
        } else if (orig == current) {
            if (dest == current) return DemandType.InvalidDemand;
            else return DemandType.TailDemand;
        } else {
            if (dest == current) return DemandType.InvalidDemand;
            else if (pass(dest)) return DemandType.HeadDemand;
            else return DemandType.IndirectDemand;
        }
    }

    private boolean isMediumDemand(int orig, int dest) {
        boolean origOccur = false;
        for (OnOff oo : records) {
            int id = oo.getId();
            if (id == orig) origOccur = true;
            if (origOccur && id == dest) return true;
        }
        return false;
    }
}