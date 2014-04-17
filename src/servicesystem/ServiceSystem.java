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
import java.util.PriorityQueue;
import java.util.Queue;
import messages.ProviderResponse;
import messages.UserResponse;
import reputationsystem.ReputationModule;
import static reputationsystem.ReputationModule.VALIDATOR_INIT;
import strategies.Strategy;
import validator.DifferenceValidator;
import validator.SimpleValidator;

//08.08: one user, set of service providers. Stubs everywhere.
//11.08: Task entity was added, reputation-hashmap handling, users
public class ServiceSystem {

    List<DifferenceValidator> validators = new ArrayList<>();
    List<Long> validationResults = new LinkedList<>();

    private final List<User> users = new ArrayList<>();
    private final Queue<Task> tasks = new PriorityQueue<>();

    Strategy experimentStrategy;
    private final ReputationModule reputationModule;
    private long servedTasksNumber = 0;

    public ServiceSystem(ExperimentData data,
            ExplorationStrategy explorationStrategy, Strategy str,
            Collection<DifferenceValidator> c) {
        data.getUsers().forEach(b -> users.add(b));
        data.getTasks().forEach(b -> tasks.add(b));
        experimentStrategy = str;
        // epsilon-decreasing exploration strategy - with default parameters
        reputationModule = new ReputationModule(data.getProviders(),
                explorationStrategy);
        validators.addAll(c);
    }

    public ServiceSystem(ExperimentData data,
            ExplorationStrategy explorationStrategy, Strategy str) {
        data.getUsers().forEach(b -> users.add(b));
        data.getTasks().forEach(b -> tasks.add(b));
        experimentStrategy = str;
        // epsilon-decreasing exploration strategy - with default parameters
        reputationModule = new ReputationModule(data.getProviders(),
                explorationStrategy);
        validators.add(new SimpleValidator(VALIDATOR_INIT));
    }

    public void submitTask(Task t) {
        tasks.add(t);
    }

    public void addUser(User u) {
        users.add(u);
    }

    public void addProvider(ServiceProvider sp) {
        reputationModule.addServiceProvider(sp);
    }

    public void run() {
        while (!tasks.isEmpty()) {
            processCurrentRequest(tasks.poll());
            servedTasksNumber++;
        }
    }

    private void processCurrentRequest(Task task) {
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
        Double delta = reputationModule.update(worker,
                ans.getEstimate(), ans.getDifferenceSign());

        checkDelta(delta);
    }

    private void checkDelta(Double delta) {
        for (DifferenceValidator d : validators) {
            if (d.isDifferenceInGap(delta)) 
                validationResults.add(servedTasksNumber);
        }
    }

    private ServiceProvider chooseProvider(Task task) {

        return reputationModule.chooseProvider(task, experimentStrategy);
    }

}
