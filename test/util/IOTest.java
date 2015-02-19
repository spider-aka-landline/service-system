package util;

import io.IO;
import Jama.Matrix;
import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;

import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Spider
 */
public class IOTest {

    public IOTest() {
    }


    /**
     * Test of readMatrixFromFile method, of class IO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testReadMatrixFromFileSquareTwo() throws Exception {
        System.out.println("readMatrixFromFile");
        String fileName = "test/matrixTestInput22.txt";
        double[][] vals = {{1., 2.}, {3., 4.}};
        Matrix expResult = new Matrix(vals);
        Matrix result = IO.readMatrixFromFile(fileName);
        assertArrayEquals(expResult.getArray(), result.getArray());
    }

    @Test
    public void testReadMatrixFromFileSquareTwoWithEmptyLine() throws Exception {
        System.out.println("readMatrixFromFile");
        String fileName = "test/matrixTestInput22_emptyLine.txt";
        double[][] vals = {{1., 2.}, {3., 4.}};
        Matrix expResult = new Matrix(vals);
        Matrix result = IO.readMatrixFromFile(fileName);
        assertArrayEquals(expResult.getArray(), result.getArray());
    }

    @Test
    public void testReadMatrixFromFileTwoColumnsFourLines() throws Exception {
        System.out.println("readMatrixFromFile");
        String fileName = "test/matrixTestInput24.txt";
        double[][] vals = {{1., 2.}, {3., 4.}, {5., 6.}, {7., 8.}};
        Matrix expResult = new Matrix(vals);
        Matrix result = IO.readMatrixFromFile(fileName);
        assertArrayEquals(expResult.getArray(), result.getArray());
    }

}
