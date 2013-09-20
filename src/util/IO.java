/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import Jama.Matrix;
import entities.Entity;
import static java.lang.System.out;
import java.util.Collection;

public class IO {
    
    public static Boolean debug = false;
    
    private static int x,y;
    private static Matrix results;
    private static Matrix ones;
    private static Matrix zeros;
    private static Integer iteration_cycle = 0;
    private static Integer task_number = 0;
    
    public static void initMatrix(Integer iterations_number, Integer task_number){
        x = iterations_number;
        y = task_number;
        results = new Matrix(x, y);
        ones = new Matrix(1, results.getRowDimension(), 1.0);
        zeros = new Matrix(results.getColumnDimension(), 1);
    }
    
    public static void printTotalResult (){
        Matrix end_result = new Matrix(1,y);
        
        for (int i=0; i < y ; i++){
            zeros.set(i, 0, 1.0);
            end_result.set(0, i, (ones.times(results.times(zeros))).det() / x );    
            
            zeros.set(i, 0, 0.0);
        }
        end_result.transpose().print(1,3);
    }
    
    public static void log(Object o){
        if (o instanceof Number) logNumber((Double)o);
        else throw new IllegalArgumentException();
    }
    
    private static void logNumber(Double value){
        results.set(iteration_cycle, task_number, value);
        if (debug) out.format("%.3f%n", value);
        task_number++;
    } 
    
    public static void nextIteration(){
        iteration_cycle++;
        task_number = 0;
        if (debug) out.format("=== Iteration %d === %n", iteration_cycle);
    }
    
    
     public static void printCollection(Collection<? extends Entity> smth){
        smth.forEach(b -> b.printProperty());
        System.out.println();
    }

        
}
