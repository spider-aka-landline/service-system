package experiments.graph;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class UniformHystogram<K extends Number> {

    private static final int NUMBER_OF_SEGMENTS = 10;

    private final Map<Double, Integer> segmentedItems = new TreeMap<>();
    Map<K, Integer> inputMap = new TreeMap<>();

    private Double maxKey;
    private Double minKey;

    private Double delimiter;

    private Double currentPointer;
    private Double currentMax;
    private Integer currentFrequency;

    public UniformHystogram(Collection<K> inputCollection) {

        inputCollection.stream().forEach((o) -> {
            Integer freq = inputMap.get(o);
            inputMap.put(o, (freq == null) ? 1 : freq + 1);
        });

        Object[] temp = inputMap.keySet().toArray();
        minKey = ((K) temp[0]).doubleValue();
        maxKey = ((K) temp[temp.length - 1]).doubleValue();

        //find the length of intervals
        delimiter = (maxKey - minKey) / NUMBER_OF_SEGMENTS;

        //init the current pointer and max
        currentPointer = minKey + delimiter / 2;
        currentMax = minKey + delimiter;
        currentFrequency = 0;
    }

    public UniformHystogram(Collection<K> inputCollection,
            double min, double max) {

        inputCollection.stream().forEach((o) -> {
            Integer freq = inputMap.get(o);
            inputMap.put(o, (freq == null) ? 1 : freq + 1);
        });

        minKey = min;
        maxKey = max;

        //find the length of intervals
        delimiter = (maxKey - minKey) / NUMBER_OF_SEGMENTS;

        //init the current pointer and max
        currentPointer = minKey + delimiter / 2;
        currentMax = minKey + delimiter;
    }

    private void parse() {

        //iteration over map
        inputMap.entrySet().stream().forEach((b) -> {
            //if object less than currentMax -> increase frequency
            if (currentMax.compareTo((Double) b.getKey()) < 0) {
                segmentedItems.put(currentPointer, currentFrequency);
                currentFrequency = 0;
                currentPointer += delimiter;
                currentMax += delimiter;
            }
            //Integer freq = segmentedItems.get(currentPointer);
            currentFrequency += b.getValue();
        });
        segmentedItems.put(currentPointer, currentFrequency);

    }

    public Map<Double, Integer> getData() {
        if (inputMap == null || inputMap.isEmpty()) {
            return null;
        }
        if (segmentedItems.isEmpty()) {
            parse();
        }
        return segmentedItems;
    }

}
