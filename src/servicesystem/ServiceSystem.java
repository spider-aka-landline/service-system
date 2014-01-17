package servicesystem;

import entities.providers.ServiceProvider;
import entities.Task;
import entities.users.User;
import exploration.ExplorationStrategy;
import java.util.ArrayList;
import java.util.Collection;
import reputationsystem.ReputationModule;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import messages.ProviderResponse;
import messages.UserResponse;

//08.08: one user, set of service providers. Stubs everywhere.
//11.08: Task entity was added, reputation-hashmap handling, users
public class ServiceSystem {

    private final List<User> users = new ArrayList<>();
    private final Queue<Task> tasks = new PriorityQueue<>();

    private final ReputationModule reputationModule;
    //boolean initTrigger = true;

    public ServiceSystem(Collection<ServiceProvider> pr,
            Collection<User> us, Collection<Task> ts,
            ExplorationStrategy strategy) {
        us.forEach(b -> users.add(b));
        ts.forEach(b -> tasks.add(b));
        // epsilon-decreasing exploration strategy - with default parameters
        reputationModule = new ReputationModule(pr, strategy);
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
        return reputationModule.chooseProvider(task);
    }

}
