package experiments.graph;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Hystogram<K extends Number> {

    public Map<K, Integer> items = new TreeMap<>();

    public Hystogram(Collection<K> inputCollection) {
        inputCollection.stream().forEach((o) -> {
            Integer freq = items.get(o);
            items.put(o, (freq == null) ? 1 : freq + 1);
        });

    }

    public Hystogram(Map<K, Integer> inputMap) {
        inputMap.entrySet().stream().forEach(b -> {
            items.put(b.getKey(), b.getValue());
        });
    }

    public Map<K, Integer> getData() {
        return items;
    }

}
