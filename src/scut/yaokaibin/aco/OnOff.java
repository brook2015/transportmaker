package scut.yaokaibin.aco;

/**
 * Created by YaoKaibin on 2015/11/25.
 */
public class OnOff {
    // id of location
    private int id;
    private int on;
    private int off;
    private int time;

    public OnOff(int id, int on, int off, int time) {
        this.id = id;
        this.on = on;
        this.off = off;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getOn() {
        return on;
    }

    public int getOff() {
        return off;
    }

    public void addOn(int number) {
        on += number;
    }

    public void addOff(int number) {
        off += number;
    }

    public int getDelta() {
        return on - off;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.format("%d +%d-%d %d", id, on, off, time);
    }
}
