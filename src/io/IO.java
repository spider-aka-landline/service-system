package io;

import Jama.Matrix;
import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;
import experiments.ExperimentData;
import experiments.ExperimentSettings;
import experiments.graph.Histogram;
import experiments.graph.UniformHistogram;
import io.files.FileCreator;
import io.files.FileNameFormatUtil;
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
    public static FileCreator fileCreator = new FileCreator();

    //public static final NumberFormat FORMAT_FOR_DOUBLE = new DecimalFormat("#.00");

    public static File getCurrentDirectory() {
        return new File(fileNameFormatUtil.getCurrentDirectoryPath());
    }

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
        return fileCreator.createFile(filepath);
    }


    public static <V> void printCollection(Collection<V> smth, String filepath) {
        printCollection(smth, filepath, "");
    }

    public static <V> void printCollection(Collection<V> smth, String filepath, String header) {
        if (smth == null || smth.isEmpty()) {
            throw new NullPointerException("Empty collection for print");
        }
        try (PrintWriter writer = new PrintWriter(createFile(filepath))) {
            writer.append("#").append(header).append("\n");
            smth.forEach(b -> writer.append(b.toString()).append("\n"));

            System.out.println(filepath);

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
                writer.append(b.getKey().toString()).append(",");
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

    public static Matrix readMatrixFromFile(String fileName) throws IOException {
        return readMatrixFromFile(new FileReader(fileName));
    }

    public static Matrix readMatrixFromFile(File file) throws IOException {
        return readMatrixFromFile(new FileReader(file));
    }

    private static Matrix readMatrixFromFile(FileReader fileReader) throws IOException {
        return Matrix.read(new BufferedReader(fileReader));
    }

    public static Collection<User> readUsers(File file) throws IOException {
        Matrix input = readMatrixFromFile(file);
        return createUsersFromMatrix(input);
    }

    public static Collection<User> readUsers(String fileName) throws IOException {
        Matrix input = readMatrixFromFile(getFilePath(fileName));
        return createUsersFromMatrix(input);
    }

    private static Collection<User> createUsersFromMatrix(Matrix input)
            throws IOException {
        Collection<User> res = new ArrayList<>();
        int x = input.getRowDimension();
        int y = input.getColumnDimension();

        for (int i = 0; i < x; i++) {
            res.add(EntitiesParser.parseUser(input.getMatrix(i, i, 0, y - 1)));
        }
        return res;
    }


    public static Collection<ServiceProvider> readProviders(String fileName)
            throws IOException {
        Matrix input = readMatrixFromFile(getFilePath(fileName));
        return createProvidersFromMatrix(input);
    }

    public static Collection<ServiceProvider> readProviders(File file)
            throws IOException {
        Matrix input = readMatrixFromFile(file);
        return createProvidersFromMatrix(input);
    }

    private static Collection<ServiceProvider> createProvidersFromMatrix(Matrix input)
            throws IOException {
        Collection<ServiceProvider> res = new ArrayList<>();
        int x = input.getRowDimension();
        int y = input.getColumnDimension();

        for (int i = 0; i < x; i++) {
            res.add(EntitiesParser.parseServiceProvider(input.getMatrix(i, i, 0, y - 1)));
        }
        return res;
    }


    public static Collection<Task> readTasks(String tasksFileName, Collection<User> usersCollection)
            throws IOException {
        Matrix input = readMatrixFromFile(getFilePath(tasksFileName));
        return createTasksFromMatrix(input, usersCollection);
    }

    public static Collection<Task> readTasks(File file, Collection<User> usersCollection)
            throws IOException {
        Matrix input = readMatrixFromFile(file);
        return createTasksFromMatrix(input, usersCollection);
    }

    public static Collection<Task> createTasksFromMatrix(Matrix input, Collection<User> usersCollection)
            throws IOException {
        Collection<Task> result = new ArrayList<>();
        int x = input.getRowDimension();
        int y = input.getColumnDimension();

        for (int i = 0; i < x; i++) {
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

    public static void printHystogramToFile(UniformHistogram evaluations,
                                            String filePath) throws FileNotFoundException {
        printMapToFile(evaluations.getSegmentedData(), filePath);
    }

    public static void printHystogramToFile(Histogram evaluations,
                                            String filePath) throws FileNotFoundException {
        printMapToFile(evaluations.getData(), filePath);
    }

}
