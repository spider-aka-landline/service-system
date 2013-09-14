/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entities.Entity;
import static java.lang.System.out;
import java.util.Collection;

public class IO {
    
    public static void log(Object o){
        if (o instanceof Number) logNumber((Number)o);
        else throw new IllegalArgumentException();
    }
    
    private static void logNumber (Number value){
        out.format("%.3f%n ", value);
    } 
    
     public static void printCollection(Collection<? extends Entity> smth){
        smth.forEach(b -> b.printProperty());
        System.out.println();
    }

        
}
