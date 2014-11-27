package strategies.newpackage;

import entities.Task;
import entities.providers.ServiceProvider;
import exploration.ExplorationStrategy;
import java.util.Map;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;
import strategies.Strategy;
import strategies.newpackage.criterae.Criteria;

public abstract class AbstractStrategy implements Strategy {

    /* Внешний метод, добытый у интерфейса
     * Должен вызывать все внутренние
     */
    @Override
    public ServiceProvider chooseProvider(Task t,
            ExplorationStrategy exploration,
            ProvidersReputationMap map) {

        //добыть множество провайдеров, способных выполнить задание пользователя
        ProvidersReputationMap acceptableProviders
                = getAcceptableProviders(t, map);

        //добыть множество поиска
        Map<ServiceProvider, DataEntity> searchSet
                = getSearchSet(acceptableProviders, exploration);
        
        //добыть критерий выбора
        Criteria criteria = getCriteria();

        //Выбрать провайдера согласно критерию из множества
        ServiceProvider sp = criteria.chooseProvider(searchSet);
        return sp;
    }

    ProvidersReputationMap
            getAcceptableProviders(Task t, ProvidersReputationMap map){
                return map;
            }

    abstract Criteria getCriteria();

    //FIXME: передаем ProvidersReputationMap, 
    //чтобы хранить информацию о принадлежности к множеству авторитетных
    abstract Map<ServiceProvider, DataEntity> 
        getSearchSet(ProvidersReputationMap acceptableProviders, 
                        ExplorationStrategy exploration);

}
