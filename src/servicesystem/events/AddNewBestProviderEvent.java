package servicesystem.events;

import entities.Params;
import entities.providers.ServiceProvider;
import myutil.IO;
import myutil.generators.EntitiesGenerator;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;
import servicesystem.ExperimentsRunner;
import servicesystem.ServiceSystemState;

import java.io.IOException;
import java.util.Map;

public class AddNewBestProviderEvent implements StressEvent {

    private final long triggerTime;
    private ServiceProvider addedProvider;
    private Boolean wereChecked;

    public AddNewBestProviderEvent(long startTime) {
        triggerTime = startTime;
        wereChecked = false;
    }

    @Override
    public void executeEvent(ServiceSystemState state) {
        addedProvider = createBetterProvider(state);

        //добавить провайдера
        state.addProvider(addedProvider);

        logAddedProvider(state);
    }

    private Params makeBetterParams(Params worstParams) {
        //Params betterParams;
        //Double betterQuality = worstParams.getServiceQuality() + 0.1;
        //if(betterQuality > 1) betterQuality = 1.0;
        //betterParams = new Params(betterQuality);
        return worstParams;
    }

    @Override
    public boolean isTriggerTime(long currentTime) {
        return (currentTime == triggerTime);
    }

    @Override
    public boolean isReadyToCheck(long currentTime) {
        return (currentTime > triggerTime) && !wereChecked;
    }

    private void logStateToFile(ServiceSystemState state) {
        Map<ServiceProvider, DataEntity> reputations = state.getProvidersReputations().getAllProvidersData();
        String filepath = "C:\\Users\\Spider\\IdeaProjects\\service-system\\results\\reputationsLog.txt";
        try {
            IO.printMapToFile(reputations, filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @Override
    public void checkCriteria(ServiceSystemState state) {
        if (state.isInReputable(addedProvider)) {
            //поднять флаг
            wereChecked = true;
            //засечь время
            long time = state.getServedTasksNumber() - triggerTime;

            /*сделать что-то с матрицами для вывода/подсчета результатов
             *точно так же, как с остальными числами
             */
            //logCriteriaCompletionTime(time);
        }
    }

    private void logCriteriaCompletionTime(long time) {
        ExperimentsRunner.logExperimentData(time);
    }

    private ServiceProvider createBetterProvider(ServiceSystemState state) {
        //получить авторитетных
        ProvidersReputationMap providersReputationMap
                = state.getProvidersReputations();
        //найти лучшего из авторитетных
        ServiceProvider bestProvider = providersReputationMap.getBestProvider();
        //забрать его характеристики
        Params betterParams = makeBetterParams(bestProvider.getProperties());
        EntitiesGenerator gen = EntitiesGenerator.getInstance();
        ServiceProvider provider = gen.createProvider(betterParams);
        if (state.hasProvider(provider)) {
            throw new IllegalArgumentException();
        }
        return provider;
    }

    private void logAddedProvider(ServiceSystemState state) {
        //check if in all providers
        Boolean inAllProvidersAfter = state.hasProvider(addedProvider);
        //check if in reputable
        Boolean inReputableProviders = state.isInReputable(addedProvider);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Provider (ID=").append(addedProvider.getID());
        stringBuilder.append(") were added (");
        stringBuilder.append(inAllProvidersAfter);
        stringBuilder.append(") but not in reputable (");
        stringBuilder.append(inReputableProviders);
        stringBuilder.append(")");

        System.out.println(stringBuilder.toString());
    }

}
