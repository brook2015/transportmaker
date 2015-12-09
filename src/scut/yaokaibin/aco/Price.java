package scut.yaokaibin.aco;

/**
 * Created by yaokaibin on 15-11-26.
 */
public class Price {
    private double ticketPrice;
    private double fuelPrice;
    private double threshold;

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double getFuelPrice() {
        return fuelPrice;
    }

    public double getThreshold() {
        return threshold;
    }

    public Price(double ticketPrice, double fuelPrice, double threshold) {
        this.ticketPrice = ticketPrice;
        this.fuelPrice = fuelPrice;
        this.threshold = threshold;
    }

    public boolean profitable(int participant, double distance) {
        return profit(participant, distance) >= threshold;
    }

    public double profit(int participant, double distance) {
        return participant * ticketPrice - distance * fuelPrice;
    }

    public static Price generalPrice() {
        return new Price(10, 2, 0);
    }
}
