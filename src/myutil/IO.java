package myutil;

import Jama.Matrix;
import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;
import experiments.ExperimentData;
import experiments.ExperimentSettings;
import experiments.graph.Hystogram;
import experiments.graph.UniformHystogram;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IO {

    public static final String RESULTS_FILEPATH = "/results/";
    public static String appendix;

    public static final NumberFormat FORMAT_FOR_DOUBLE
            = new DecimalFormat("#.00");

    public static void setAppendix(String s) {
        appendix = s;
    }

    public static String getResultsFilePath(String filename) {
        return getFilePath(RESULTS_FILEPATH+filename, true);
    }

    public static String getFilePath(String filename) {
        return getFilePath(filename, true);
    }

    public static String getFilePath(String filename, boolean addNumbers) {
        if (filename == null) {
            throw new NullPointerException("Empty filename");
        }
        String baseDir = System.getProperty("user.dir");

        /* if (baseDir.matches("([\\S]+\\/)")) {
         baseDir.replaceAll("\\/", "\\");
         }*/
        StringBuilder filepath = new StringBuilder(baseDir);
        filepath.append(RESULTS_FILEPATH);
        if (addNumbers) {
            filepath.append(appendix);
        }
        filepath.append(filename);
        return filepath.toString();
    }

    private static String getFilenameFromPath(String filepath) {
        String[] sa;
        sa = filepath.split("//");
        return sa[sa.length - 1];
    }

    //гланды автогеном, но оно ж должно заработать :(
    private static String getDirectoryFromPath(String filepath) {
        String[] sa = filepath.split("/");
        StringBuilder dir = new StringBuilder();
        for (int i = 0; i < sa.length - 1; i++) {
            dir.append(sa[i]).append("/");
        }
        return dir.toString();
    }

    private static File createFile(String filepath) {
        try {
            if (filepath.contains("\\")) {
                filepath = filepath.replace("\\", "/");
            }
            createDirectory(getDirectoryFromPath(filepath));

            File f = new File(filepath);
            if (!f.exists()) {
                if (!f.createNewFile()) {
                    throw new IOException("Cannot create file.");
                }
            }
            return f;
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, filepath, ex);
            return null;
        }

    }

    private static void createDirectory(String dirPath) throws IOException {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Cannot create directory.");
            }
        }
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

    public static void printReputationsToFile(Map<ServiceProvider, Matrix> smth, String filepath)
            throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(createFile(filepath))) {
            smth.entrySet().forEach(b -> {
                //writer.append(String.valueOf(b.getKey().getID())).append(" ");
                b.getValue().print(writer,0,5);
            });
        }
    }

    public static void printMatrixToFile(Matrix matrx,
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

    public static void printHystogramToFile(UniformHystogram evaluations,
            String filePath) throws FileNotFoundException {
        printMapToFile(evaluations.getSegmentedData(), filePath);
    }

    public static void printHystogramToFile(Hystogram evaluations,
            String filePath) throws FileNotFoundException {
        printMapToFile(evaluations.getData(), filePath);
    }

}
