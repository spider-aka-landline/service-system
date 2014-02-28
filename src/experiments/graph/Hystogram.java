package experiments.graph;

import Jama.Matrix;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Hystogram<K extends Number> {

    public Map<K, Integer> inputMap = new TreeMap<>();
    public final Double maxKey;
    public final Double minKey;

    public Hystogram(Collection<K> inputCollection) {
        inputCollection.stream().forEach((o) -> {
            Integer freq = inputMap.get(o);
            inputMap.put(o, (freq == null) ? 1 : freq + 1);
        });
        
        Object[] temp = inputMap.keySet().toArray();
        minKey = ((K) temp[0]).doubleValue();
        maxKey = ((K) temp[temp.length - 1]).doubleValue();
    }

    public Hystogram(Matrix inputMatrix) {
        if (inputMatrix.getColumnDimension() > 1) {
            throw new IllegalArgumentException("Matrix doesn't fit");
        }
        
        int rows = inputMatrix.getRowDimension();
        K obj;
        for (int i=0; i<rows; i++){
            obj = (K) Double.valueOf(inputMatrix.get(i, 0));
            Integer freq = inputMap.get(obj);
            inputMap.put(obj, (freq == null) ? 1 : freq + 1);
        }
        
        Object[] temp = inputMap.keySet().toArray();
        minKey = ((K) temp[0]).doubleValue();
        maxKey = ((K) temp[temp.length - 1]).doubleValue();
    }

    public Map<K, Integer> getData() {
        return inputMap;
    }

}
