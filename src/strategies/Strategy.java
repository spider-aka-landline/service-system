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

    /* внешний метод
     * инициирует принятие решение о провайдере 
     */
    public ServiceProvider chooseProvider(Task t,
            ExplorationStrategy exploration,
            ProvidersReputationMap map);
}
