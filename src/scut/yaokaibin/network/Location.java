package scut.yaokaibin.network;

/**
 * Created by YaoKaibin on 2015/11/22.
 */
public class Location {
    private final int id;
    private final double lat;
    private final double lng;

    public Location(int id, double lat, double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return String.format("%d(%.2f,%.2f)", id, lat, lng);
    }

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public static void main(String[] args) {
        Location location = new Location(1, 23.8259, 120.6654);
        System.out.println(location);
    }
}
