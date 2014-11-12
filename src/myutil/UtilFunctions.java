package myutil;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import math.StdRandom;

public class UtilFunctions {

    public static <K, V> Map<K, V> filterMapByPredicate(Map<K, V> map,
            Predicate<? super Map.Entry<K, V>> predicate) {
        return map.entrySet().stream().
                filter(predicate).
                collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    /* Вернуть элемент карты, value которого максимальна 
     * согласно переданному компаратору */
    public static <K, V> V getMaxValue(Map<K, V> map, Comparator<V> cmp) {
        if (map.isEmpty()) {
            return null;
        }
        return Collections.max(map.values(), cmp);
    }

    /* Вернуть элемент карты, value которого минимальна 
     * согласно переданному компаратору */
    public static <K, V> V getMinValue(Map<K, V> map, Comparator<V> cmp) {
        if (map.isEmpty()) {
            return null;
        }
        return Collections.min(map.values(), cmp);
    }
    

    /* Вернуть случайный ключ карты */
    public static <K, V> K chooseRandomElement(Map<K, V> map) {
        if (map.isEmpty()) {
            return null;
        }
        return (K) chooseRandomElement(map.keySet());
    }

    /* Вернуть случайный элемент коллекции */
    public static <V> V chooseRandomElement(Collection<V> c) {
        if (c.isEmpty()) {
            return null;
        }
        return (V) c.toArray()[StdRandom.uniform(c.size())];
    }

}