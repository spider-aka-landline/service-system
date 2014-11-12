/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package strategies;

import entities.Task;
import entities.providers.ServiceProvider;
import exploration.ExplorationStrategy;
import reputationsystem.ProvidersReputationMap;

/**
 *
 * @author Spider
 */
public interface Strategy {

    public ServiceProvider chooseProviderForTask(Task t,
            ExplorationStrategy exploration,
            ProvidersReputationMap map);
}
