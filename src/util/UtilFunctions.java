package util;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UtilFunctions {

    public static <K, V> Map<K, V> mapFilterPredicate(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
        return map.entrySet().stream().
                filter(predicate).
                collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }
 
    
}
