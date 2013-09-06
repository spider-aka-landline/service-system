package servicesystem;

import entities.ServiceProvider;
import entities.Task;
import entities.User;
import java.util.ArrayList;
import reputationsystem.ReputationModule;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import messages.UserResponse;

//08.08: one user, set of service providers. Stubs everywhere.
//11.08: Task entity was added, reputation-hashmap handling, UserSet
public class ServiceSystem {

    List<User> UserSet;
    Queue<Task> TaskQueue;

    public ReputationModule ProvidersReputations;
    boolean initTrigger = true;

    ServiceSystem() {
        ProvidersReputations = new ReputationModule();
        UserSet = new ArrayList();
        UserSet.add(new User());
        TaskQueue = new PriorityQueue();
    }

    /*1. Initiate user request queue.
     * While 1 user: simply add random;
     * While more users:  
     * - identify user in request;
     * - change request generation; */
    private void initTaskQueue() {
        for (long i = 0; i < 100; i++) {
            TaskQueue.add(new Task());
        }
    }

    private void submitTask(Task t) throws NullPointerException {
        TaskQueue.add(t);
    }

    public void run() {
        //init whole system
        initTaskQueue();
        initProvidersSet();
        processingAllRequests();
    }

    private void processingAllRequests() throws NullPointerException {
        while (!TaskQueue.isEmpty()) {
            processCurrentRequest(TaskQueue.poll());
        }
    }

    private void processCurrentRequest(Task task) {
        //выбрать провайдера
        ServiceProvider worker = chooseProvider(task);
        //узнать, кто пользователь
        User sender = task.getUser();
        //предоставитиь сервис + узнать оценки
        UserResponse ans = sender.generateResponse(worker.processUserTask(task));
        
        //пересчитать ф-ю ожидаемой ценности
        //внести изменения в репутацию
        ProvidersReputations.update(worker,ans.getEstimate(), ans.isDifferencePositive());

    }

    private ServiceProvider chooseProvider(Task task) {
        return ProvidersReputations.chooseProvider(task);
    }

    private void initProvidersSet() {
        ProvidersReputations.addServiceProvider(new ServiceProvider());
        ProvidersReputations.addServiceProvider(new ServiceProvider());
    }

}
