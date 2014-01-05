package reputationsystem;

import java.util.Comparator;

public class ExpectationComparator implements Comparator<DataEntity>{
    
    @Override
    public int compare(DataEntity o1, DataEntity o2) {
        return (o1.getExpectation() < o2.getExpectation()) ? -1
                : (o1.getExpectation() > o2.getExpectation()) ? 1 : 0;
    }
}
