package strategies.newpackage.criterae;

import entities.providers.ServiceProvider;
import java.util.Map;
import io.UtilFunctions;
import reputationsystem.DataEntity;

public class RandomCriteria implements Criteria {

    @Override
    public ServiceProvider
            chooseProvider(Map<ServiceProvider, DataEntity> searchSet) {
        return UtilFunctions.chooseRandomKey(searchSet);
    }

}
