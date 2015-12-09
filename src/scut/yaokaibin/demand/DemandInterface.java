package scut.yaokaibin.demand;

/**
 * Created by yaokaibin on 15-12-2.
 */
public interface DemandInterface {
    boolean validate();
    double calcProbability();
    void moveForward();
    void removeDemand(DemandPool pool);
}
