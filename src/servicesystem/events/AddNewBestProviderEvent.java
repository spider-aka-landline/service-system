package servicesystem.events;

import entities.Params;
import entities.providers.ServiceProvider;
import myutil.generators.EntitiesGenerator;
import reputationsystem.ProvidersReputationMap;
import servicesystem.ServiceSystem;

public abstract class AddNewBestProviderEvent implements StressEvent {

    long triggerTime;
    
    public AddNewBestProviderEvent(long timeStep){
        triggerTime = timeStep;
    }

    @Override
    public void executeEvent(ServiceSystem system) {
        //получить авторитетных
        ProvidersReputationMap providersReputationMap
                = system.getReputationModule().getprovidersReputationMap();
        //найти минимального из авторитетных
        ServiceProvider worstProvider = providersReputationMap.getWorstProvider();
        //забрать его характеристики
        Params worstParams = worstProvider.getProperties();

        //создать провайдера с лучшими характеристиками 
        //TODO: здесь надо создать характеристики, лучше чем минимальные
        //сейчас такие же характеристики
        //добавить провайдера
        EntitiesGenerator gen = EntitiesGenerator.getInstance();
        system.addProvider(gen.createProvider(worstParams));
    }

    @Override
    public boolean isTriggerTime(long currentTime) {
        return (currentTime == triggerTime);
    }

}
