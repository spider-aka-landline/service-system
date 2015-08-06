package experiments.graph;

import Jama.Matrix;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class UniformHistogram extends Histogram<Double> {

    private static final int NUMBER_OF_SEGMENTS = 10;

    private final Map<Double, Integer> segmentedItems = new TreeMap<>();

    private final Double delimiter;

    private Double currentPointer;
    private Double currentMax;
    private Integer currentFrequency;

    public UniformHistogram(Collection<Double> inputCollection) {
        super(inputCollection);
        //find the length of intervals
        delimiter = (maxKey - minKey) / NUMBER_OF_SEGMENTS;

        //init the current pointer and max
        currentPointer = minKey + delimiter / 2;
        currentMax = minKey + delimiter;
        currentFrequency = 0;
    }


    public UniformHistogram(Matrix inputMatrix) {
        super(inputMatrix);

        //find the length of intervals
        delimiter = (maxKey - minKey) / NUMBER_OF_SEGMENTS;

        //init the current pointer and max
        currentPointer = minKey + delimiter / 2;
        currentMax = minKey + delimiter;
        currentFrequency = 0;

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

    
    public Map<Double, Integer> getSegmentedData() {
        return segmentedItems;
    }

}
