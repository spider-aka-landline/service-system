package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import Jama.Matrix;

import entities.providers.ServiceProvider;
import entities.users.User;
import entities.Task;
import experiments.ExperimentData;
import experiments.ExperimentSettings;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IO {

    public static <V> void printCollection(Collection<V> smth, String filepath)
            throws FileNotFoundException {
        if (smth == null) {
            throw new NullPointerException("Empty collection for print");
        }
        try (PrintWriter writer = new PrintWriter(new File(filepath))) {
            smth.forEach(b -> writer.append(b.toString()).append("\n"));
        }
    }

    public static void printMatrixToFile(Matrix matrx, String filename, int width, int d)
            throws FileNotFoundException {

        String filePath = getFilePath(filename);
        File f = new File(filePath);
        try (PrintWriter pw = new PrintWriter(f);) {
            matrx.print(pw, 1, 3);
        }
    }

    public static String getFilePath(String filename) {
        if (filename == null) {
            throw new NullPointerException("Empty filename");
        }
        String baseDir = System.getProperty("user.dir");

        StringBuilder filepath = new StringBuilder(baseDir);
        filepath.append("/results/").append(filename);
        return filepath.toString();
    }

    public static Matrix readMatrixFromFile(String fileName) throws IOException {
        return Matrix.read(new BufferedReader(new FileReader(fileName)));
    }

    //FIXME: should read Matrix from file
    public static Collection<User> readUsers(String fileName)
            throws FileNotFoundException {
        Collection<User> res = new ArrayList<>();
        //Collection<Double> smth = readDoubleVectorFromFile(fileName);

        //smth.forEach(b -> res.add(new User(new Params(b))));
        return res;
    }

    //FIXME: should read Matrix from file
    public static Collection<ServiceProvider> readProviders(String fileName)
            throws FileNotFoundException {
        Collection<ServiceProvider> res = new ArrayList<>();
        //Collection<Double> smth = readDoubleVectorFromFile(fileName);

        //smth.forEach(b -> res.add(new ServiceProvider(new Params(b))));
        return res;
    }

    //FIXME: didn't work for more then one argument !!
    public static Collection<Task> readTasks(String tasksFileName)
            throws FileNotFoundException {
        Collection<Task> result = new ArrayList<>();
        // Collection<Integer> itemp = readIntegerVectorFromFile(tasksFileName);
        // or read matrix with 2 or more parameters

        return result;
    }

    public static void logExperimentInitData(ExperimentData data,
            ExperimentSettings settings) throws FileNotFoundException {
        printCollection(data.getUsers(),
                getFilePath(settings.getUsersFilename()));
        printCollection(data.getProviders(),
                getFilePath(settings.getProvidersFilename()));
        printCollection(data.getTasks(),
                getFilePath(settings.getTasksFilename()));
    }

}
