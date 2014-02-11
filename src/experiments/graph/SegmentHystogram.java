package experiments.graph;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

class SegmentHystogram<V extends Number> extends Hystogram{

    Map<V, Integer> segmentedItems = new TreeMap<>();

    SegmentHystogram(Collection<V> input) {
        super(input);
        //items.keySet().iterator().
    }
    
    void init(){
        double maximum = Math.rint(findMax());
        double minimum = findMin();
        double delimiter = (maximum - minimum) / 10;
    }

    private double findMax() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private double findMin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
