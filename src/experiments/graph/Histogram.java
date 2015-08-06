package experiments.graph;

import Jama.Matrix;

import java.util.*;
import java.util.stream.Stream;

public class Histogram<K extends Double> {

    public Map<K, Integer> inputMap = new TreeMap<>();
    Comparator<K> cmp = Double::compareTo;

    public final K maxKey;
    public final K minKey;

    public Histogram(Collection<K> inputCollection) {
        inputCollection.stream().forEach((K o) -> {
            Integer freq = inputMap.get(o);
            inputMap.put(o, (freq == null) ? 1 : freq + 1);
        });

        minKey = inputMap.keySet().stream().min(cmp).get();
        maxKey = inputMap.keySet().stream().max(cmp).get();
    }

    public Histogram(Matrix inputMatrix) {
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

        minKey = inputMap.keySet().stream().min(cmp).get();
        maxKey = inputMap.keySet().stream().max(cmp).get();
    }



    public Map<K, Integer> getData() {
        return inputMap;
    }

}
