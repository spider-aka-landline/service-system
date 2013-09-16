package servicesystem;

import entities.ServiceProvider;
import entities.Task;
import entities.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import util.IO;
import util.Poisson;
import util.StdRandom;

public class Main {

    static Integer iterations_number;

    static Collection<User> us_list;
    static Collection<ServiceProvider> pr_list;
    static Collection<Task> tasks;

    static Integer user_number;
    static Integer providers_number;
    static Integer tasks_number;
    static Boolean generateAll;

    public static void main(String... args) {
        iterations_number = 100;

        generateAll = true;

        user_number = 10;
        providers_number = 20;
        tasks_number = 5000;

        if (generateAll) {
            us_list = initUsers(user_number);
            pr_list = initProviders(providers_number);
            tasks = generateTasks(tasks_number);
            printSystemInitState();
        }

        ServiceSystem Test = new ServiceSystem();
        Test.initServiceSystem(pr_list, us_list, tasks);
        
        IO.initMatrix(iterations_number, tasks_number);
        for (int i = 0; i < iterations_number; i++) {
            Test.run();
            Test.resetToState(pr_list, us_list, tasks);
            IO.nextIteration();
        }
       IO.printTotalResult();
    }

    private static Collection<User> initUsers(Integer us_n) {
        Collection<User> tempU = new HashSet();
        for (int i = 0; i < us_n; i++) {
            tempU.add(new User(StdRandom.uniform(0, 1)));
        }
        return tempU;
    }

    private static Collection<ServiceProvider> initProviders(Integer pr_n) {
        Collection<ServiceProvider> tempP = new HashSet();
        for (int i = 0; i < pr_n; i++) {
            tempP.add(new ServiceProvider(StdRandom.uniform(0, 1)));
        }
        return tempP;
    }

    private static Collection<Task> generateTasks(Integer t_n) {
        Collection<Task> tempT = new ArrayList();
        Poisson generator = new Poisson(42.0);
        for (int i = 0; i < t_n; i++) {
            tempT.add(new Task(us_list.iterator().next(), generator.next()));
        }
        return tempT;
    }

    private static void printSystemInitState() {
        System.out.println("Users, needed_quality");
        IO.printCollection(us_list);
        System.out.println("Providers, service_quality");
        IO.printCollection(pr_list);
        System.out.println("Tasks, creationTime");
        IO.printCollection(tasks);
    }

}
