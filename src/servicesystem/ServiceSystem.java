package servicesystem;

import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;
import experiments.ExperimentData;
import exploration.ExplorationStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import messages.ProviderResponse;
import messages.UserResponse;
import reputationsystem.ProvidersReputationMap;
import strategies.Strategy;
import validator.DifferenceValidator;
import validator.SimpleValidator;

//08.08: one user, set of service providers. Stubs everywhere.
//11.08: Task entity was added, reputation-hashmap handling, users
public class ServiceSystem {

    List<DifferenceValidator> validators = new ArrayList<>();
    List<Long> validationResults = new LinkedList<>();
    Boolean checked = false;

    protected final ServiceSystemState state;
    Strategy experimentStrategy;

    private final ExplorationStrategy explorationStrategy;

    public ServiceSystem(ExperimentData data,
            ExplorationStrategy exploreStrategy, Strategy str,
            Collection<DifferenceValidator> c) {

        state = new ServiceSystemState(data);
        explorationStrategy = exploreStrategy;
        experimentStrategy = str;
        validators.addAll(c);
    }

    public ServiceSystem(ExperimentData data,
            ExplorationStrategy exploreStrategy, Strategy str) {
        state = new ServiceSystemState(data);
        explorationStrategy = exploreStrategy;
        experimentStrategy = str;
        validators.add(new SimpleValidator());
    }

    public Long getvalidationResults() {
        return validationResults.get(0);
    }

    public ServiceSystemState getSystemState() {
        return state;
    }

    public void run() {
        checked = false;
        while (state.hasTasks()) {
            processCurrentRequest(state.pollTask());
            state.incrementServedTasksNumber();
        }
    }

    protected void processCurrentRequest(Task task) {
        //выбрать провайдера
        ServiceProvider worker = chooseProvider(task);
        //узнать, кто пользователь
        User sender = task.getSender();
        //предоставитиь сервис
        ProviderResponse service = worker.processUserTask(task);
        //узнать оценки
        UserResponse ans = sender.generateResponse(service);
        //пересчитать ф-ю ожидаемой ценности
        //внести изменения в репутацию
        //
        Double delta = state.update(worker,
                ans.getEstimate(), ans.getDifferenceSign());

        checkDelta(delta);
    }

    private void checkDelta(Double delta) {
        for (DifferenceValidator d : validators) {
            if (!checked && d.isDifferenceInGap(delta)) {
                validationResults.add(state.getServedTasksNumber());
                checked = true;
            } else if (checked && !d.isDifferenceInGap(delta)) {
                //validationResults.remove(validationResults.size() - 1);
                checked = false;
            }
        }

    }

    /**
     * epsilon-decreasing стратегия выбора провайдера
     *
     * @param task User task
     * @return ServiceProvider, chosen for the task
     */
    protected ServiceProvider chooseProvider(Task task) {
        ProvidersReputationMap reputations = state.getprovidersReputations();
        if (reputations.isEmpty()) {
            throw new RuntimeException("No service providers were found. Can't serve request.");
        }

        return experimentStrategy.chooseProviderForTask(task,
                explorationStrategy, reputations);
    }

}
