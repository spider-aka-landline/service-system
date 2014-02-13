package servicesystem;

import entities.providers.ServiceProvider;
import entities.Task;
import entities.users.User;
import experiments.ExperimentData;
import exploration.ExplorationStrategy;
import java.util.ArrayList;
import reputationsystem.ReputationModule;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import messages.ProviderResponse;
import messages.UserResponse;
import reputationsystem.ChooseProviderStrategy;

//08.08: one user, set of service providers. Stubs everywhere.
//11.08: Task entity was added, reputation-hashmap handling, users
public class ServiceSystem {

    private final List<User> users = new ArrayList<>();
    private final Queue<Task> tasks = new PriorityQueue<>();

    ChooseProviderStrategy experimentStrategy;
    private final ReputationModule reputationModule;

    public ServiceSystem(ExperimentData data,
            ExplorationStrategy explorationStrategy, ChooseProviderStrategy str) {
        data.getUsers().forEach(b -> users.add(b));
        data.getTasks().forEach(b -> tasks.add(b));
        experimentStrategy = str;
        // epsilon-decreasing exploration strategy - with default parameters
        reputationModule = new ReputationModule(data.getProviders(), explorationStrategy);
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
        reputationModule.update(worker, ans.getEstimate(), ans.getDifferenceSign());

    }

    private ServiceProvider chooseProvider(Task task) {
        return reputationModule.chooseProvider(task, experimentStrategy);
    }

}
