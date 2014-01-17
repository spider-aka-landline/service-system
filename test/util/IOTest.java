package util;

import Jama.Matrix;
import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;
import java.io.File;
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
     * Test of printCollection method, of class IO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testPrintCollection() throws Exception {
        System.out.println("printCollection");
        //IO.printCollection(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFilePath method, of class IO.
     */
    @Test
    public void testGetFilePath() {
        System.out.println("getFilePath");
        String fileName = "";
        String expResult = "";
        String result = IO.getFilePath(fileName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeToFile method, of class IO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testWriteToFile_File_String() throws Exception {
        System.out.println("writeToFile");
        File file = null;
        String data = "";
        IO.writeToFile(file, data);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeToFile method, of class IO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testWriteToFile_String_String() throws Exception {
        System.out.println("writeToFile");
        String filePath = "";
        String data = "";
        IO.writeToFile(filePath, data);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readFromFile method, of class IO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testReadFromFile_File() throws Exception {
        System.out.println("readFromFile");
        File file = null;
        String expResult = "";
        String result = IO.readFromFile(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readFromFile method, of class IO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testReadFromFile_String() throws Exception {
        System.out.println("readFromFile");
        String fileName = "";
        String expResult = "";
        String result = IO.readFromFile(fileName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
    public void testReadMatrixFromFileTwoColumnsFourLines() throws Exception {
        System.out.println("readMatrixFromFile");
        String fileName = "test/matrixTestInput24.txt";
        double[][] vals = {{1., 2.}, {3., 4.}, {5., 6.}, {7., 8.}};
        Matrix expResult = new Matrix(vals);
        Matrix result = IO.readMatrixFromFile(fileName);
        assertArrayEquals(expResult.getArray(), result.getArray());
    }

    /**
     * Test of readDoubleVectorFromFile method, of class IO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testReadDoubleVectorFromFile() throws Exception {
        System.out.println("readDoubleVectorFromFile");
        String fileName = "";
        Collection<Double> expResult = null;
        Collection<Double> result = IO.readDoubleVectorFromFile(fileName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readIntegerVectorFromFile method, of class IO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testReadIntegerVectorFromFile() throws Exception {
        System.out.println("readIntegerVectorFromFile");
        String fileName = "";
        Collection<Integer> expResult = null;
        Collection<Integer> result = IO.readIntegerVectorFromFile(fileName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readDoubleMatrixFromFile method, of class IO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testReadDoubleMatrixFromFile() throws Exception {
        System.out.println("readDoubleMatrixFromFile");
        String fileName = "";
        Collection<Integer> expResult = null;
        Collection<Integer> result = IO.readDoubleMatrixFromFile(fileName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readUsers method, of class IO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testReadUsers() throws Exception {
        System.out.println("readUsers");
        String fileName = "";
        Collection<User> expResult = null;
        Collection<User> result = IO.readUsers(fileName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readProviders method, of class IO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testReadProviders() throws Exception {
        System.out.println("readProviders");
        String fileName = "";
        Collection<ServiceProvider> expResult = null;
        Collection<ServiceProvider> result = IO.readProviders(fileName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readTasks method, of class IO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testReadTasks() throws Exception {
        System.out.println("readTasks");
        String tasksFileName = "";
        Collection<Task> expResult = null;
        Collection<Task> result = IO.readTasks(tasksFileName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
