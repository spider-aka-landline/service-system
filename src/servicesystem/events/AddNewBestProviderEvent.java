package servicesystem.events;

import entities.Params;
import entities.providers.ServiceProvider;
import myutil.generators.EntitiesGenerator;
import reputationsystem.ProvidersReputationMap;
import servicesystem.ServiceSystemState;

public class AddNewBestProviderEvent implements StressEvent {

    private long triggerTime;
    private ServiceProvider addedProvider;
    private Boolean wereChecked;

    public AddNewBestProviderEvent(long timeStep) {
        triggerTime = timeStep;
        wereChecked = false;
    }

    @Override
    public void executeEvent(ServiceSystemState state) {
        //получить авторитетных
        ProvidersReputationMap providersReputationMap
                = state.getprovidersReputations();
        //найти минимального из авторитетных
        ServiceProvider worstProvider = providersReputationMap.getWorstProvider();
        //забрать его характеристики
        //создать провайдера с лучшими характеристиками 
        Params betterParams = makeBetterParams(worstProvider.getProperties());
        EntitiesGenerator gen = EntitiesGenerator.getInstance();
        addedProvider = gen.createProvider(betterParams);

        //добавить провайдера
        state.addProvider(addedProvider);
    }

    private Params makeBetterParams(Params worstParams) {
        Params betterParams;
        betterParams = new Params(worstParams.getServiceQuality() + 0.1);
        return betterParams;
    }

    @Override
    public boolean isTriggerTime(long currentTime) {
        return (currentTime == triggerTime);
    }

    @Override
    public boolean isReadyToCheck(long currentTime) {
        return (currentTime > triggerTime)&& wereChecked;
    }

    @Override
    public void checkCriteria(ServiceSystemState state) {
        if (state.isInReputable(addedProvider)){
            //поднять флаг
            wereChecked = true;
            //засечь время
            long time = state.getServedTasksNumber();
            
            /*сделать что-то с матрицами для вывода/подсчета результатов
             *точно так же, как с остальными числами
             */
            
        }
    }

}
