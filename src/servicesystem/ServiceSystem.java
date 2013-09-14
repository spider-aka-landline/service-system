package servicesystem;

import entities.ServiceProvider;
import entities.Task;
import entities.User;
import java.util.ArrayList;
import java.util.Collection;
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
 
    public ServiceSystem(Collection<ServiceProvider> pr_list, 
            Collection<User> us_list, Collection<Task> tasks) {
        
        //Users. Useless at this point.
        UserSet = new ArrayList();
        us_list.forEach(b -> UserSet.add(b));
        
        //Tasks
        TaskQueue = new PriorityQueue();
        tasks.forEach(b -> TaskQueue.add(b));
        
        //Providers -> to reputation module
        ProvidersReputations = new ReputationModule(pr_list);
    }


    private void submitTask(Task t) throws NullPointerException {
        TaskQueue.add(t);
    }

    public void run() {
        
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


}
