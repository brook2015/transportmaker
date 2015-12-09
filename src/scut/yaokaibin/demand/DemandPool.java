package scut.yaokaibin.demand;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by YaoKaibin on 2015/11/22.
 */
public class DemandPool {
    private int count;
    private Vector<TravelDemand> demands;
    //private static Price price = Price.generalPrice();

    public Collection<TravelDemand> getDemands() {
        return demands.stream().filter(d -> !d.serve()).collect(Collectors.toList());
    }

    public DemandPool() {
        this.count = 0;
        this.demands = new Vector<>();
    }

    public DemandPool(Collection<TravelDemand> demands) {
        this();
        this.demands.addAll(demands);
        this.count += demands.size();
    }

    public DemandPool(Scanner scanner) {
        this();
        int c = scanner.nextInt();
        if (c < 0) throw new IllegalArgumentException("Count of demand is invalid.");
        for (int i = 0; i < c; i++) {
            int orig = scanner.nextInt();
            int dest = scanner.nextInt();
            int number = scanner.nextInt();
            int time = scanner.nextInt();
            TravelDemand info = new TravelDemand(orig, dest, number, time);
            addDemand(info);
        }
    }

    public void addDemand(TravelDemand demand) {
        if (demand == null) throw new NullPointerException("Travel demand is null.");
        demands.add(demand);
        count++;
    }

    public List<TravelDemand> getDemands(Predicate<TravelDemand> predicate) {
        return demands.stream().filter(predicate).collect(Collectors.toList());
    }

    public int getCount() {
        return count;
    }

    public void mark(int id) {
        Optional<TravelDemand> optional = demands.stream().filter(d -> id == d.getIndex()).findFirst();
        if (optional.isPresent()) {
            optional.get().setServe(true);
        }
    }

    public void recover() {
        demands.forEach(d -> d.setServe(false));
    }

    public Iterable<Integer> getVertex() {
        Set<Integer> vertex = new HashSet<>();
        demands.forEach(d -> {
            vertex.add(d.getOrig());
            vertex.add(d.getDest());
        });
        return vertex;
    }

    @Override
    public String toString() {
        String result = "";
        for (TravelDemand demand : demands) {
            result += demand + ";";
        }
        return result;
    }

    public int getTotalTraveller() {
        int total = 0;
        for (TravelDemand demand : demands) total += demand.getTravellerNum();
        return total;
    }
}
