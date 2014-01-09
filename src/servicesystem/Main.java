package servicesystem;

import java.util.Collection;

import entities.providers.ServiceProvider;
import entities.users.User;
import entities.Task;
import exploration.EpsilonDecreasingStrategy;
import exploration.ExplorationStrategy;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.IO;
import util.Generator;

public class Main {

    static Collection<User> users;
    static Collection<ServiceProvider> providers;
    static Collection<Task> tasks;

    static String USERS_FILENAME = "users.txt";
    static String PROVIDERS_FILENAME = "providers.txt";
    static String TASKS_FILENAME = "tasks.txt";

    static String RESULTS_FILENAME = "results.txt";

    static Integer ITERATIONS_NUMBER = 1;
    static Integer USERS_NUMBER = 2;
    static Integer PROVIDERS_NUMBER = 4;
    static Integer TASKS_NUMBER = 50;

    static Boolean generateAll = true; //didn't work for false now

    public static void main(String... args) {

        if (generateAll) {

            Generator gen = new Generator();
            users = gen.generateUsers(USERS_NUMBER);
            providers = gen.generateProviders(PROVIDERS_NUMBER);
            tasks = gen.generateTasks(users, TASKS_NUMBER);
            try {
                IO.printCollection(users, USERS_FILENAME);
                IO.printCollection(providers, PROVIDERS_FILENAME);
                IO.printCollection(tasks, TASKS_FILENAME);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                users = IO.readUsers(USERS_FILENAME);
                providers = IO.readProviders(PROVIDERS_FILENAME);
                //tasks = IO.readTasks(TASKS_FILENAME);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        ExplorationStrategy strategy = new EpsilonDecreasingStrategy();
        ServiceSystem test;

        IO.initMatrix(ITERATIONS_NUMBER, tasks.size());
        for (int i = 0; i < ITERATIONS_NUMBER; i++) {
            test = new ServiceSystem(providers, users, tasks, strategy);
            test.run();
            IO.nextIteration();
        }

        try {
            IO.printTotalResult(RESULTS_FILENAME);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
