package servicesystem;

import entities.Params;
import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;
import experiments.ExperimentData;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import reputationsystem.ProvidersReputationMap;
import reputationsystem.ReputationModule;

public class ServiceSystemState {

    private final List<User> users = new ArrayList<>();
    private final Queue<Task> tasks = new PriorityQueue<>();
    private final ReputationModule reputationModule;
    private long servedTasksNumber = 0;

    public ServiceSystemState(ExperimentData data) {
        users.addAll(data.getUsers());
        tasks.addAll(data.getTasks());

        // epsilon-decreasing exploration strategy - with default parameters
        reputationModule = new ReputationModule(data.getProviders());
    }

    public long getServedTasksNumber() {
        return servedTasksNumber;
    }

    public void incrementServedTasksNumber() {
        servedTasksNumber++;
    }

    boolean hasTasks() {
        return !tasks.isEmpty();
    }

    Task pollTask() {
        return tasks.poll();
    }

    public ProvidersReputationMap getProvidersReputations() {
        return reputationModule.getprovidersReputationMap();
    }

    public void addProvider(ServiceProvider provider) {
        reputationModule.addServiceProvider(provider);
    }

    public void submitTask(Task t) {
        tasks.add(t);
    }

    public void addUser(User u) {
        users.add(u);
    }

    Double update(ServiceProvider worker, Double estimate,
            Boolean differenceSign) {
        return reputationModule.update(worker, estimate, differenceSign);
    }

    public Boolean isInReputable(ServiceProvider addedProvider) {
        return reputationModule.isInReputable(addedProvider);
    }

    public boolean hasProvider(ServiceProvider provider) {
        return reputationModule.contains(provider);
    }

    public void changeProviderParams(ServiceProvider changingProvider, Params changedParams) {
        reputationModule.update(changingProvider, changedParams);
    }

    public void removeProvider(ServiceProvider addedProvider) {
        reputationModule.removeProvider(addedProvider);
    }
}
