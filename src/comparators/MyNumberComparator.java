package comparators;

import java.util.Comparator;

public class MyNumberComparator implements Comparator<Number> {

    @Override
    public int compare(Number o1, Number o2) {
        double d1 = Math.abs(o1.doubleValue());
        double d2 = o2.doubleValue();

        return (d1 < d2) ? -1
                : (d1 > d2) ? 1 : 0;
    }

}
