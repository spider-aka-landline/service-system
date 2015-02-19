package io;

import Jama.Matrix;
import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;
import experiments.ExperimentData;
import experiments.ExperimentSettings;
import experiments.graph.Hystogram;
import experiments.graph.UniformHystogram;
import reputationsystem.DataEntity;
import servicesystem.ServiceSystemState;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IO {

    private static final String logChangingProvidersFilepath
            = "C:\\Users\\Spider\\IdeaProjects\\service-system\\results\\changingProvidersLog.txt";
    private static final String serviceSystemStateLogFilepath
            = "C:\\Users\\Spider\\IdeaProjects\\service-system\\results\\reputationsLog.txt";


    public static FileNameFormatUtil fileNameFormatUtil = new FileNameFormatUtil();
    public static FileSystemUtil fileSystemUtil = new FileSystemUtil();

    //public static final NumberFormat FORMAT_FOR_DOUBLE = new DecimalFormat("#.00");

    public static void setAppendix(String s) {
        fileNameFormatUtil.setAppendix(s);
    }

    public static String getFilePath(String filename) {
        return fileNameFormatUtil.getAbsoluteFilePath(filename);
    }

    public static String getFilePath(String filename, boolean addNumbers) {
        return fileNameFormatUtil.getAbsoluteFilePath(filename, addNumbers);
    }

    public static File createFile(String filepath) {
        return fileSystemUtil.createFile(filepath);
    }


    public static <V> void printCollection(Collection<V> smth, String filepath) {
        if (smth == null) {
            throw new NullPointerException("Empty collection for print");
        }
        try (PrintWriter writer = new PrintWriter(createFile(filepath))) {
            smth.forEach(b -> writer.append(b.toString()).append("\n"));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(IO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static <V> void printAdd(V smth, String filepath) {
        createFile(filepath);
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filepath, true)))) {
            if (smth == null) {
                writer.println("null");
            } else {
                writer.println(smth);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(IO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static <V> void logChangingProvider(V smth) {
        printAdd(smth, logChangingProvidersFilepath);
    }

    public static <K, V> void printMapToFile(Map<K, V> smth, String filepath)
            throws FileNotFoundException {
        if (smth == null) {
            throw new NullPointerException("Empty map for print");
        }
        try (PrintWriter writer = new PrintWriter(createFile(filepath))) {
            smth.entrySet().forEach(b -> {
                writer.append(b.getKey().toString()).append(" ");
                writer.append(b.getValue().toString()).append("\n");
            });
        }
    }

    public static void logServiceSystemStateToFile(ServiceSystemState state) {
        Map<ServiceProvider, DataEntity> reputations = state.getProvidersReputations().getAllProvidersData();
        try {
            IO.printMapToFile(reputations, serviceSystemStateLogFilepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printReputationsToFile(Map<ServiceProvider, Matrix> reputationsMap, String filepath)
            throws FileNotFoundException {
        if (reputationsMap == null) {
            throw new NullPointerException("Empty map for print");
        }
        String fullPath = getFilePath(filepath);
        Matrix reputations = createPrintableReputations(reputationsMap);
        printMatrixToFile(reputations, fullPath);
    }

    private static Matrix createPrintableReputations(Map<ServiceProvider, Matrix> reputationsMap) {
        int tempColumnDimension = ((Matrix) reputationsMap.values().toArray()[0]).getColumnDimension();

        //form new Matrix
        int x = reputationsMap.size();
        int y = tempColumnDimension + 1;
        Matrix reputations = new Matrix(x, y);

        //for each line add to new Matrix
        int counter = 0;
        for (Map.Entry<ServiceProvider, Matrix> entry : reputationsMap.entrySet()) {
            reputations.set(counter, 0, entry.getKey().getID().getValue());
            reputations.setMatrix(counter, counter, 1, tempColumnDimension, entry.getValue());
            counter++;
        }
        return reputations.transpose();
    }

    public static void printMatrixToFile(Matrix matrx, String filename) throws FileNotFoundException {
        printMatrixToFile(matrx, filename, 0, 5);
    }

    private static void printMatrixToFile(Matrix matrx,
                                          String filename, int width, int d) throws FileNotFoundException {

        String filePath = getFilePath(filename);
        try (PrintWriter pw = new PrintWriter(createFile(filePath));) {
            matrx.print(pw, width, d);
        }
    }

    public static Matrix readMatrixFromFile(String fileName)
            throws IOException {
        return Matrix.read(new BufferedReader(new FileReader(fileName)));
    }

    public static Collection<User> readUsers(String fileName)
            throws IOException {
        Collection<User> res = new ArrayList<>();
        Matrix input = readMatrixFromFile(getFilePath(fileName));
        int x = input.getRowDimension();
        int y = input.getColumnDimension();

        for (int i=0; i<x; i++) {
            res.add(EntitiesParser.createUser(input.getMatrix(i, i, 0, y - 1)));
        }
        return res;
    }

    public static Collection<ServiceProvider> readProviders(String fileName)
            throws IOException {
        Collection<ServiceProvider> res = new ArrayList<>();
        Matrix smth = readMatrixFromFile(getFilePath(fileName));

        int x = smth.getRowDimension();
        int y = smth.getColumnDimension();

        for (int i=0; i<x; i++) {
            res.add(EntitiesParser.createServiceProvider(smth.getMatrix(i, i, 0, y - 1)));
        }
        return res;
    }

    public static Collection<Task> readTasks(String tasksFileName, Collection<User> usersCollection)
            throws IOException {
        Collection<Task> result = new ArrayList<>();
        Matrix input = readMatrixFromFile(getFilePath(tasksFileName));
        int x = input.getRowDimension();
        int y = input.getColumnDimension();

        for (int i=0; i<x; i++) {
            result.add(EntitiesParser.createTask(input.getMatrix(i, i, 0, y - 1), usersCollection));
        }

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

    public static void printHystogramToFile(UniformHystogram evaluations,
                                            String filePath) throws FileNotFoundException {
        printMapToFile(evaluations.getSegmentedData(), filePath);
    }

    public static void printHystogramToFile(Hystogram evaluations,
                                            String filePath) throws FileNotFoundException {
        printMapToFile(evaluations.getData(), filePath);
    }

}
