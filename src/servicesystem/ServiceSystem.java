package servicesystem;

import entities.providers.ServiceProvider;
import entities.Task;
import entities.users.User;
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

    private List<User> UserSet;
    private Queue<Task> TaskQueue;

    private ReputationModule ProvidersReputations;
    //boolean initTrigger = true;
 
    public ServiceSystem() {
        UserSet = new ArrayList();
        TaskQueue = new PriorityQueue();
        ProvidersReputations = new ReputationModule();
    }
    
    public void initServiceSystem(Collection<ServiceProvider> pr_list, 
        Collection<User> us_list, Collection<Task> tasks) {
        us_list.forEach(b -> UserSet.add(b));        
        tasks.forEach(b -> TaskQueue.add(b));
        ProvidersReputations.initProviders(pr_list);
    }


    public void submitTask(Task t) {
        TaskQueue.add(t);
    }
    
    public void addUser(User u) {
        UserSet.add(u);
    }
    
    public void addProvider(ServiceProvider sp) {
        ProvidersReputations.addServiceProvider(sp);
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
        User sender = task.getSender();
        //предоставитиь сервис + узнать оценки
        UserResponse ans = sender.generateResponse(worker.processUserTask(task));
        
        //пересчитать ф-ю ожидаемой ценности
        //внести изменения в репутацию
        ProvidersReputations.update(worker,ans.getEstimate(), ans.isDifferencePositive());

    }

    private ServiceProvider chooseProvider(Task task) {
        return ProvidersReputations.chooseProvider(task);
    }

    public void resetToState(Collection<ServiceProvider> pr_list, 
            Collection<User> us_list, Collection<Task> tasks) {
        UserSet.clear();
        TaskQueue.clear();
        ProvidersReputations.clear();
        initServiceSystem(pr_list, us_list, tasks);
    }


}
