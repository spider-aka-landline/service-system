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
import java.util.Collection;
import myutil.Generator;
import myutil.IO;

public class ExperimentData {

    private Integer iterationNumber;

    private final Integer usersNumber;
    private final Integer providersNumber;
    private final Integer tasksNumber;

    private final Collection<User> users;
    private final Collection<ServiceProvider> providers;
    private final Collection<Task> tasks;

    /**
     *
     * @param usersc Users number
     * @param providersc Providers number
     * @param tasksc Tasks number
     * @param iterationsc total iterations number
     */
    public ExperimentData(Integer usersc, Integer providersc, Integer tasksc,
            Integer iterationsc) {
        iterationNumber = iterationsc;
        usersNumber = usersc;
        providersNumber = providersc;
        tasksNumber = tasksc;
        Generator gen = new Generator();
        users = gen.generateUsers(usersNumber);
        providers = gen.generateProviders(providersNumber);
        tasks = gen.generateTasks(users, tasksNumber);

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
        Generator gen = new Generator();
        tasks = gen.generateTasks(users, tasksNumber);

    }

    /**
     *
     * @param usersfile users data filename
     * @param providersfile providers data filename
     * @param tasksfile tasks data filename
     * @throws FileNotFoundException
     */
    public ExperimentData(String usersfile, String providersfile,
            String tasksfile) throws FileNotFoundException {
        users = IO.readUsers(usersfile);
        providers = IO.readProviders(providersfile);
        tasks = IO.readTasks(tasksfile);
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
