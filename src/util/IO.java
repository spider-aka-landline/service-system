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
import entities.Task;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IO {

    public static <V> void printCollection(Collection<V> smth, String filePath)
            throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            smth.forEach(b -> writer.append(b.toString()).append("\n"));
        }
    }

    public static void printMatrixToFile(Matrix matrx, String fileName, int width, int d)
            throws FileNotFoundException {
        
        String filePath = getFilePath(fileName);
        File f = new File(filePath);
        try (PrintWriter pw = new PrintWriter(f);) {
            matrx.print(pw, 1, 3);
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

    public static Matrix readMatrixFromFile(String fileName) throws IOException {
        return Matrix.read(new BufferedReader(new FileReader(fileName)));
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

    //FIXME: should read Matrix from file
    public static Collection<User> readUsers(String fileName)
            throws FileNotFoundException {
        Collection<User> res = new ArrayList<>();
        //Collection<Double> smth = readDoubleVectorFromFile(fileName);

        //smth.forEach(b -> res.add(new User(new Params(b))));
        return res;
    }

    //FIXME: should read Matrix from file
    public static Collection<ServiceProvider> readProviders(String fileName) throws FileNotFoundException {
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

}
