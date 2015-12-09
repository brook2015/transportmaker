package scut.yaokaibin.network;

/**
 * Created by yaokaibin on 15-12-4.
 */
public class ShortestEdge {
    private int orig;
    private int dest;
    private double distance;
    private double time;

    public ShortestEdge(int orig, int dest, double distance, double time) {
        this.orig = orig;
        this.dest = dest;
        this.distance = distance;
        this.time = time;
    }

    public int getOrig() {
        return orig;
    }

    public int getDest() {
        return dest;
    }

    public double getDistance() {
        return distance;
    }

    public double getTime() {
        return time;
    }
}
