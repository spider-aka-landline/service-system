package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import Jama.Matrix;

import entities.providers.ServiceProvider;
import entities.users.User;
import entities.Params;
import entities.Task;

public class IO {

    public static Boolean debug = false;

    private static int x, y;
    private static Matrix results;
    private static Matrix ones;
    private static Matrix zeros;
    private static Integer iterationCycle = 0;
    private static Integer taskNumber = 0;

    public static void initMatrix(Integer iterations, Integer tasks) {
        x = iterations;
        y = tasks;
        results = new Matrix(x, y);
        ones = new Matrix(1, results.getRowDimension(), 1.0);
        zeros = new Matrix(results.getColumnDimension(), 1);
    }

    public static void log(Double value) {
        results.set(iterationCycle, taskNumber, value);
        if (debug) {
            out.format("%.3f%n", value);
        }
        taskNumber++;
    }

    public static void nextIteration() {
        iterationCycle++;
        taskNumber = 0;
        if (debug) {
            out.format("=== Iteration %d === %n", iterationCycle);
        }
    }

    public static <V> void printCollection(Collection<V> smth, String filePath)
            throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            smth.forEach(b -> writer.append(b.toString()).append("\n"));
        }
    }

    public static void printTotalResult(String fileName)
            throws FileNotFoundException {
        Matrix result = new Matrix(1, y);

        for (int i = 0; i < y; i++) {
            zeros.set(i, 0, 1.0);
            result.set(0, i, (ones.times(results.times(zeros))).det() / x);

            zeros.set(i, 0, 0.0);
        }

        //public void print(PrintWriter writer, NumberFormat nf, int i)
        String filePath = getFilePath(fileName);
        File f = new File(filePath);

        try (PrintWriter pw = new PrintWriter(f);) {
            result.transpose().print(pw, 1, 3);
        }
    }

    public static String getFilePath(String fileName) {
        String baseDir = System.getProperty("user.dir");
        String filePath = baseDir + "/results/" + fileName;
        return filePath;
    }

    public static void writeToFile(File file, String data)
            throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(data);
        }
    }

    public static void writeToFile(String filePath, String data)
            throws FileNotFoundException {
        writeToFile(new File(filePath), data);
    }

    public static String readFromFile(File file)
            throws FileNotFoundException {
        StringBuilder data;
        try (Scanner reader = new Scanner(file)) {
            reader.useDelimiter("\\n");
            data = new StringBuilder();
            while (reader.hasNext()) {
                data.append(reader.next()).append("\n");
            }
        }
        return data.toString();
    }

    public static String readFromFile(String fileName)
            throws FileNotFoundException {
        return readFromFile(new File(fileName));
    }

    public static Collection<Double> readDoubleVectorFromFile(String fileName)
            throws FileNotFoundException {
        Collection<Double> res = new ArrayList<>();
        try (Scanner reader = new Scanner(new File(fileName))) {
            reader.useDelimiter("\\n");
            while (reader.hasNext()) {
                res.add(Double.valueOf(reader.next()));
            }
        }
        return res;
    }

    //FIXME!!!
    public static Collection<Integer> readIntegerVectorFromFile(String fileName)
            throws FileNotFoundException {
        Collection<Integer> res = new ArrayList<>();
        try (Scanner reader = new Scanner(new File(fileName))) {
            reader.useDelimiter("\\n");
            while (reader.hasNext()) {
                res.add(Integer.valueOf(reader.next()));
            }
        }
        return res;
    }

    public static Collection<Integer> readDoubleMatrixFromFile(String fileName)
            throws FileNotFoundException {
        Collection<Integer> res = new ArrayList<>();
        try (Scanner reader = new Scanner(new File(fileName))) {
            reader.useDelimiter("\\n");
            while (reader.hasNext()) {
                res.add(Integer.valueOf(reader.next()));
            }
        }
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

    public static Collection<User> readUsers(String fileName) 
            throws FileNotFoundException {
        Collection<User> res = new ArrayList<>();
        Collection<Double> smth = readDoubleVectorFromFile(fileName);
        //FIXME: should read Matrix from file
        smth.forEach(b -> res.add(new User(new Params(b))));
        return res;
    }

    public static Collection<ServiceProvider> readProviders(String fileName) throws FileNotFoundException {
        Collection<ServiceProvider> res = new ArrayList<>();
        Collection<Double> smth = readDoubleVectorFromFile(fileName);
        //FIXME: should read Matrix from file
        smth.forEach(b -> res.add(new ServiceProvider(new Params(b))));
        return res;
    }

}
