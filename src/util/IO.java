/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import Jama.Matrix;
import entities.Entity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.Collection;
import java.util.Scanner;

public class IO {

    public static Boolean debug = false;

    private static int x, y;
    private static Matrix results;
    private static Matrix ones;
    private static Matrix zeros;
    private static Integer iteration_cycle = 0;
    private static Integer task_number = 0;

    public static void initMatrix(Integer iterations_number, Integer task_number) {
        x = iterations_number;
        y = task_number;
        results = new Matrix(x, y);
        ones = new Matrix(1, results.getRowDimension(), 1.0);
        zeros = new Matrix(results.getColumnDimension(), 1);
    }

    public static void log(Object o) {
        if (o instanceof Number) {
            logNumber((Double) o);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static void logNumber(Double value) {
        results.set(iteration_cycle, task_number, value);
        if (debug) {
            out.format("%.3f%n", value);
        }
        task_number++;
    }

    public static void nextIteration() {
        iteration_cycle++;
        task_number = 0;
        if (debug) {
            out.format("=== Iteration %d === %n", iteration_cycle);
        }
    }

    public static void printCollection(Collection<? extends Entity> smth) {
        smth.forEach(b -> b.printProperty());
        System.out.println();
    }

    public static void printTotalResult() throws FileNotFoundException {
        Matrix end_result = new Matrix(1, y);

        for (int i = 0; i < y; i++) {
            zeros.set(i, 0, 1.0);
            end_result.set(0, i, (ones.times(results.times(zeros))).det() / x);

            zeros.set(i, 0, 0.0);
        }

        //public void print(PrintWriter writer, NumberFormat nf, int i)
        String filePath = getFilePath("average_profit.txt");
        File f = new File(filePath);

        try (PrintWriter pw = new PrintWriter(f);) {
            end_result.transpose().print(pw, 1, 3);
        }
    }

    public static String getFilePath(String fileName) {
        String baseDir = System.getProperty("user.dir");
        String filePath = baseDir + "/results/" + fileName;
        return filePath;
    }

    public static void writeToFile(File file, String data) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(data);
        }
    }

    public static void writeToFile(String filePath, String data) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            writer.write(data);
        }
    }

    public static String readFromFile(File file) throws FileNotFoundException {
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

}
