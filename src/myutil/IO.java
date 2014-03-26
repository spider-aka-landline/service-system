package myutil;

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
import experiments.graph.Hystogram;
import experiments.graph.UniformHystogram;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IO {

    public static final String RESULTS_FILEPATH = "/results/";
    public static String appendix;

    public static final String GNUPLOT_SCRIPT_FILENAME = "h.plot";
    public static final NumberFormat FORMAT_FOR_DOUBLE
            = new DecimalFormat("#.00");

    public static void setAppendix(String s) {
        appendix = s;
    }

    public static String getGnuplotScriptFilepath() {
        return getFilePath(GNUPLOT_SCRIPT_FILENAME);
    }

    public static String getFilePath(String filename) {
        if (filename == null) {
            throw new NullPointerException("Empty filename");
        }
        String baseDir = System.getProperty("user.dir");
        
       /* if (baseDir.matches("([\\S]+\\/)")) {
            baseDir.replaceAll("\\/", "\\");
        }*/

        StringBuilder filepath = new StringBuilder(baseDir);
        filepath.append(RESULTS_FILEPATH).append(appendix);

        File myPath = new File(filepath.toString());
        myPath.mkdirs();
        System.out.println(filepath.toString());

        filepath.append(filename);
        return filepath.toString();
    }

    private static String getFilenameFromPath(String filepath) {
        String[] sa;
        sa = filepath.split("/");
        return sa[sa.length - 1];
    }

    public static File createFile(String filepath) {

        File f = new File(filepath, getFilenameFromPath(filepath));
        try {
            if (f.createNewFile()) {
                return f;
            } else {
                return null;
            }
        } catch (IOException ex) {
            System.err.println(filepath);
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
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

    public static void printMatrixToFile(Matrix matrx,
            String filename, int width, int d) throws FileNotFoundException {

        String filePath = getFilePath(filename);
        File f = new File(filePath, filename);
        try (PrintWriter pw = new PrintWriter(f);) {
            matrx.print(pw, 1, 3);
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
