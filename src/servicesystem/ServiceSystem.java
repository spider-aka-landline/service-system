package servicesystem;

import entities.providers.ServiceProvider;
import entities.Task;
import entities.users.User;
import exploration.EpsilonDecreasingStrategy;
import java.util.ArrayList;
import java.util.Collection;
import reputationsystem.ReputationModule;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import messages.UserResponse;

//08.08: one user, set of service providers. Stubs everywhere.
//11.08: Task entity was added, reputation-hashmap handling, Users
public class ServiceSystem {

    private final List<User> Users = new ArrayList<>();
    private final Queue<Task> Tasks = new PriorityQueue<>();

    private final ReputationModule Reputations;
    //boolean initTrigger = true;

    public ServiceSystem(Collection<ServiceProvider> pr,
            Collection<User> us, Collection<Task> ts) {
        us.forEach(b -> Users.add(b));
        ts.forEach(b -> Tasks.add(b));
        // epsilon-decreasing exploration strategy - with default parameters
        Reputations = new ReputationModule(pr, new EpsilonDecreasingStrategy());
    }

    public void submitTask(Task t) {
        Tasks.add(t);
    }

    public void addUser(User u) {
        Users.add(u);
    }

    public void addProvider(ServiceProvider sp) {
        Reputations.addServiceProvider(sp);
    }

    public void run() {
        while (!Tasks.isEmpty()) {
            processCurrentRequest(Tasks.poll());
        }
    }

    private void processCurrentRequest(Task task) {
        //выбрать провайдера
        ServiceProvider worker = chooseProvider(task);
        //узнать, кто пользователь
        User sender = task.getSender();
        //предоставитиь сервис + узнать оценки
        UserResponse ans = sender.generateResponse(worker.processUserTask(task));

        //пересчитать ф-ю ожидаемой ценности
        //внести изменения в репутацию
        Reputations.update(worker, ans.getEstimate(), ans.getDifferenceSign());

    }

    private ServiceProvider chooseProvider(Task task) {
        return Reputations.chooseProvider(task);
    }

}
