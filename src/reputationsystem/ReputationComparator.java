package reputationsystem;

import java.util.Comparator;

public class ReputationComparator implements Comparator<DataEntity>{
    
    @Override
    public int compare(DataEntity o1, DataEntity o2) {
        return (o1.getReputation() < o2.getReputation()) ? -1
                : (o1.getReputation() > o2.getReputation()) ? 1 : 0;
    }
}
