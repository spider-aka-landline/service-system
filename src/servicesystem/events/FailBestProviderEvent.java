package servicesystem.events;

import entities.Params;
import entities.providers.ServiceProvider;
import io.IO;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;
import servicesystem.ExperimentsRunner;
import servicesystem.ServiceSystemState;

import java.io.IOException;
import java.util.Map;

 public class FailBestProviderEvent extends ChangeProviderEvent {

    public FailBestProviderEvent(long startTime) {
        super(startTime);
    }

    public FailBestProviderEvent(long startTime, long endTime) {
        super(startTime, endTime);
    }


     @Override
     protected ServiceProvider getChangingProvider(ServiceSystemState state) {
         ProvidersReputationMap providersReputationMap
                 = state.getProvidersReputations();
         ServiceProvider bestProvider = providersReputationMap.getBestProvider();

         return bestProvider;
     }

     @Override
     protected Params getChangedParams(ServiceSystemState state) {
         return new Params(0.);
     }

     @Override
     protected boolean isCriteriaSatisfied(ServiceSystemState state) {
         return !state.isInReputable(changingProvider);
     }


 }
