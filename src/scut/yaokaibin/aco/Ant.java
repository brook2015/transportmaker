package scut.yaokaibin.aco;

import scut.yaokaibin.demand.*;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by YaoKaibin on 2015/11/22.
 */
public class Ant {
    private boolean keepGoing;
    private static Random random = new Random();
    private Record record;
    private PheromoneGraph graph; // record pheromone
    private int totalTraveller;

    public Ant(PheromoneGraph graph) {
        this.graph = graph;
    }

    public Record getRecord() {
        return record;
    }

    public void initiate(DemandPool pool) {
        keepGoing = true;
        record = new Record();
        totalTraveller = pool.getTotalTraveller();
        Predicate<TravelDemand> predicate = t -> {
            Price price = Price.generalPrice();
            boolean profitable = price.profitable(t.getTravellerNum(), t.getDistance());
            boolean serve = t.serve();
            return profitable && !serve;
        };
        List<TravelDemand> qualified = pool.getDemands(predicate);
        int size = qualified.size();
        if (size == 0) throw new IllegalArgumentException("No suitable demand");

        TravelDemand firstDemand = qualified.get(random.nextInt(size));
        firstDemand.initiate(record);
        pool.mark(firstDemand.getIndex());
    }

    public boolean isKeepGoing() {
        return keepGoing;
    }

    public void moveForward(DemandPool source) throws NullPointerException {
        if (!keepGoing) return;

        Collection<TravelDemand> demands = source.getDemands();
        if (0 == demands.size()) {
            keepGoing = false;
            return;
        }

        Vector<DemandInterface> qualified = new Vector<>();
        for (TravelDemand demand : demands) {
            DemandType type = record.type(demand);
            switch (type) {
                case HeadDemand:
                    qualified.add(new HeadDemand(demand, record, graph));
                    break;
                case MediumDemand:
                    qualified.add(new MediumDemand(demand, record, graph));
                    break;
                case TailDemand:
                    qualified.add(new TailDemand(demand, record, graph));
                    break;
                case IndirectDemand:
                    qualified.add(new IndirectDemand(demand, record, graph));
                    break;
                default:
                    break;
            }
        }

        qualified.removeIf(c -> !c.validate());
        if (0 == qualified.size()) {
            keepGoing = false;
            return;
        }
        DemandInterface selectedDemand = roulette(probability(qualified));
        selectedDemand.moveForward();
        selectedDemand.removeDemand(source);
    }

    private Map<DemandInterface, Double> probability(Collection<DemandInterface> source) {
        int size = source.size();
        if (0 == size) return null;
        Map<DemandInterface, Double> probability = new HashMap<>(size);
        source.forEach(d -> {
            double prob = d.calcProbability();
            if (Double.isInfinite(prob)) throw new IllegalArgumentException("Value is finite.");
            probability.put(d, prob);
        });
        return probability;
    }

    private DemandInterface roulette(Map<DemandInterface, Double> source) {
        double sum = 0.0;
        for (Double value : source.values()) sum += value;
        double accumulation = 0.0;
        double rand = random.nextDouble();

        for (Map.Entry<DemandInterface, Double> entry : source.entrySet()) {
            accumulation += entry.getValue();
            if (rand < accumulation / sum) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void pheromoneAccumulate() {
        Iterator<Integer> iterator = record.getSequence();
        int orig = iterator.hasNext() ? iterator.next() : -1;
        if (orig != -1) {
            while (iterator.hasNext()) {
                int dest = iterator.next();
                double delta = getRatio();
                graph.addPheromone(orig, dest, delta);
                orig = dest;
            }
        }
    }

    public void pheromoneUpdate() {
        pheromoneVolatilize();
        pheromoneAccumulate();
    }

    public void pheromoneVolatilize() {
        graph.volatilize();
    }

    public double getProfit() {
        return record.profit();
    }

    public void pheromoneDistribute() {
        System.out.println(graph);
    }

    private double pheromoneDelta(double value) {
        return 2.0 / (1 + Math.exp(-0.02 * value)) - 1;
    }

    public double getRatio() {
        int totalOn = record.getTotalTravellerNum();
        return (double) totalOn / (double) totalTraveller;
    }
}
