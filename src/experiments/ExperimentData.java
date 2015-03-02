/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experiments;

import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;
import entities.generators.EntitiesGenerator;
import io.IO;

public class ExperimentData {

    protected Integer iterationNumber;

    protected final Integer usersNumber;
    protected final Integer providersNumber;
    protected final Integer tasksNumber;

    protected final Collection<User> users;
    protected final Collection<ServiceProvider> providers;
    protected final Collection<Task> tasks;

    /**
     *
     * @param usersc Users number
     * @param providersc Providers number
     * @param tasksc Tasks number
     * @param iterationsc total iterations number
     */
    public ExperimentData(Integer usersc, Integer providersc, Integer tasksc,
            Integer iterationsc) {
        this(usersc, providersc, tasksc, iterationsc, true);
    }

    /**
     *
     * @param usersc Users number
     * @param providersc Providers number
     * @param tasksc Tasks number
     * @param iterationsc total iterations number
     * @param generate - standard generation or no generation at all
     */
    protected ExperimentData(Integer usersc, Integer providersc, Integer tasksc,
            Integer iterationsc, Boolean generate) {
        iterationNumber = iterationsc;
        usersNumber = usersc;
        providersNumber = providersc;
        tasksNumber = tasksc;

        users = new ArrayList<>();
        providers = new ArrayList<>();
        tasks = new PriorityQueue<>();

        if (generate) {
            EntitiesGenerator gen = EntitiesGenerator.getInstance();

            users.addAll(gen.generateUsers(usersNumber));
            providers.addAll(gen.generateProviders(providersNumber));
            tasks.addAll(gen.generateTasks(users, tasksNumber));
        }
    }
    
    /**
     *
     * @param usersCollection collection of given users
     * @param providersCollection collection of given providers
     * @param tasksc Tasks number
     * @param iterationsc total iterations number
     */
    public ExperimentData(Collection<User> usersCollection,
            Collection<ServiceProvider> providersCollection,
            Integer tasksc, Integer iterationsc) {
        iterationNumber = iterationsc;
        users = usersCollection;
        providers = providersCollection;

        usersNumber = users.size();
        providersNumber = providers.size();
        tasksNumber = tasksc;

        EntitiesGenerator gen = EntitiesGenerator.getInstance();
        tasks = gen.generateTasks(users, tasksNumber);

    }

    /**
     *
     * @param usersCollection collection of given users
     * @param providersCollection collection of given providers
     * @param tasksCollection collection of given tasks
     * @param iterationsc total iterations number
     * @throws FileNotFoundException
     */
    public ExperimentData(Collection<User> usersCollection,
                          Collection<ServiceProvider> providersCollection,
                          Collection<Task> tasksCollection, Integer iterationsc) throws IOException {
        iterationNumber = iterationsc;
        users = usersCollection;
        providers = providersCollection;

        usersNumber = users.size();
        providersNumber = providers.size();

        tasks = tasksCollection;
        tasksNumber = tasks.size();
    }

    /**
     *
     * @param usersfile users data filename
     * @param providersfile providers data filename
     * @param tasksfile tasks data filename
     * @throws FileNotFoundException
     */
    public ExperimentData(String usersfile, String providersfile,
            String tasksfile) throws IOException {
        users = IO.readUsers(usersfile);
        providers = IO.readProviders(providersfile);
        tasks = IO.readTasks(tasksfile, users);
        usersNumber = users.size();
        providersNumber = providers.size();
        tasksNumber = tasks.size();
    }

    public Collection<User> getUsers() {
        return users;
    }

    public Collection<ServiceProvider> getProviders() {
        return providers;
    }

    public Collection<Task> getTasks() {
        return tasks;
    }

    public Integer getIterationsNumber() {
        return iterationNumber;
    }

    public Integer getUsersNumber() {
        return usersNumber;
    }

    public Integer getProvidersNumber() {
        return providersNumber;
    }

    public Integer getTasksNumber() {
        return tasksNumber;
    }

}
