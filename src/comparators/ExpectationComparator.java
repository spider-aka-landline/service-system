package comparators;

import java.util.Comparator;
import reputationsystem.DataEntity;

public class ExpectationComparator implements Comparator<DataEntity>{
    
    @Override
    public int compare(DataEntity o1, DataEntity o2) {
        return (o1.getExpectation() < o2.getExpectation()) ? -1
                : (o1.getExpectation() > o2.getExpectation()) ? 1 : 0;
    }
}
