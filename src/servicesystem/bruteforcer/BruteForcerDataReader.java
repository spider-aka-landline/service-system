package servicesystem.bruteforcer;

import entities.DipoleData;
import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;
import io.IO;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Spider on 05.02.2015.
 */
public class BruteForcerDataReader {

    private static final String inputFilepath = "inputData\\";
    private static BruteForcerData readedData = new BruteForcerData();

    public static BruteForcerData readData() throws IOException {
        Collection<ServiceProvider> providers;
        Collection<User> users;

        IO.setAppendix(inputFilepath);
        providers = IO.readProviders("providers.txt");
        users = IO.readUsers("users.txt");

        int p = providers.size();
        int u = users.size();
        readedData.addNumbers(new DipoleData(u, p));
        readedData.initProvidersBase(p, providers);
        readedData.putUsers(u, users);
        return readedData;
    }

    public static Collection<Task> readTasks(Collection<User> users, Integer tasksNumber) {
        IO.setAppendix(inputFilepath);
        StringBuilder sb = new StringBuilder();
        sb.append("tasks_u").append(users.size()).append("_t").append(tasksNumber).append(".txt");

        Collection<Task> tasks = null;
        try {
            tasks = IO.readTasks(sb.toString(), users);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tasks;
    }
}
