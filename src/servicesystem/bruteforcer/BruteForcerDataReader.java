package servicesystem.bruteforcer;

import entities.DipoleData;
import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;
import io.IO;
import io.files.FileLister;
import io.files.filters.InputDataFileFilter;
import servicesystem.bruteforcer.patterns.FilenamePattern;
import servicesystem.bruteforcer.patterns.ProvidersFilenamePattern;
import servicesystem.bruteforcer.patterns.TasksFilenamePattern;
import servicesystem.bruteforcer.patterns.UsersFilenamePattern;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Spider on 05.02.2015.
 */
public class BruteForcerDataReader {

    private static final String inputFilepath = "inputData\\";
    private static File currentDirectory;
    private static BruteForcerData readedData = new BruteForcerData();


    private static void initIO() {
        IO.setAppendix(inputFilepath);
        currentDirectory = IO.getCurrentDirectory();
        if (!currentDirectory.isDirectory())
            throw new IllegalArgumentException("Not a directory: while reading init data");
    }

    public static BruteForcerData readData() throws IOException {
        initIO();

        Collection<ServiceProvider> providers;
        Collection<User> users;

        FileLister fileLister = new FileLister();
        //users
        String usersPattern = new UsersFilenamePattern().getFilenamePattern();
        InputDataFileFilter usersFilter = new InputDataFileFilter(usersPattern);
        Collection<File> usersFiles = fileLister.getFilteredFilesInDirectory(currentDirectory, usersFilter);
        //providers
        String providersPattern = new ProvidersFilenamePattern().getFilenamePattern();
        InputDataFileFilter providersFilter = new InputDataFileFilter(providersPattern);
        Collection<File> providersFiles = fileLister.getFilteredFilesInDirectory(currentDirectory, providersFilter);

        //for each file in dir
        for (File currentUsersFile : usersFiles)
            for (File currentProvidersFile : providersFiles) {
                users = IO.readUsers(currentUsersFile);
                providers = IO.readProviders(currentProvidersFile);
                int u = users.size();
                int p = providers.size();
                readedData.addNumbers(new DipoleData(u, p));
                readedData.initProvidersBase(p, providers);
                readedData.putUsers(u, users);
            }

        return readedData;
    }

    public static BruteForcerData readData(Collection<DipoleData> dipoles) throws IOException {
        initIO();

        Collection<ServiceProvider> providers;
        Collection<User> users;

        for (DipoleData currentDipoleData : dipoles) {
            //get numbers
            int providersNumber = currentDipoleData.getProviderNumber();
            int usersNumber = currentDipoleData.getUserNumber();

            //read providers
            providers = readProviders(providersNumber);
            //read users
            users = readUsers(usersNumber);

            readedData.addNumbers(currentDipoleData);
            readedData.initProvidersBase(providersNumber, providers);
            readedData.putUsers(usersNumber, users);
        }
        return readedData;
    }

    private static Collection<ServiceProvider> readProviders(int providersNumber) {
        FilenamePattern pattern = new ProvidersFilenamePattern(providersNumber);
        Collection<ServiceProvider> providers = null;
        try {
            providers = IO.readProviders(pattern.getFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkEqualSize(providers.size(), providersNumber);
        return providers;
    }

    private static Collection<User> readUsers(int usersNumber) {
        FilenamePattern pattern = new UsersFilenamePattern(usersNumber);
        Collection<User> users = null;
        try {
            users = IO.readUsers(pattern.getFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkEqualSize(users.size(), usersNumber);
        return users;
    }


    private static void checkEqualSize(int readedDataSize, int declaredDataSize) {
        if (readedDataSize != declaredDataSize) throw new IllegalArgumentException("Wrong data size in file");
    }

    public static Collection<Task> readTasks(Collection<User> users, Integer tasksNumber) {
        initIO();
        FilenamePattern pattern = new TasksFilenamePattern(users.size(), tasksNumber);
        Collection<Task> tasks = null;
        try {
            tasks = IO.readTasks(pattern.getFilename(), users);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tasks;
    }

}
