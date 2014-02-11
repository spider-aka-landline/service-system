package experiments.graph;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

class Hystogram<V extends Number> {

    Map<V, Integer> items = new TreeMap<>();

    Hystogram(Collection<V> input) {
        input.stream().forEach((o) -> {
            Integer freq = items.get(o);
            items.put(o, (freq == null) ? 1 : freq + 1);
        });
    }

    
}
