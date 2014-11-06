package comparators;

import entities.Params;
import entities.providers.ServiceProvider;
import java.util.Comparator;

public class QualityParamsComparator implements Comparator<ServiceProvider> {

    @Override
    public int compare(ServiceProvider o1, ServiceProvider o2) {
        Params p1 = o1.getProperties();
        Params p2 = o2.getProperties();
        double m1 = metrics(p1);
        double m2 = metrics(p2);
                
        return (m1 < m2) ? -1 : (m1 > m2) ? 1 : 0;
    }

    private Double metrics(Params params) {
        return params.getServiceQuality();
    }

}
