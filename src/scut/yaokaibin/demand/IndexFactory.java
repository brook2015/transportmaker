package scut.yaokaibin.demand;

/**
 * Created by yaokaibin on 15-11-26.
 */
public class IndexFactory {
    private int index;

    public IndexFactory() {
        index = 0;
    }

    public synchronized int getIndex() {
        try {
            if (index == Integer.MAX_VALUE)
                throw new Exception("overflow");
            index += 1;
            return index;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
