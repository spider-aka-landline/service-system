package servicesystem;

import entities.DipoleData;
import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {
        int providersQuantity = 30;
        int usersQuantity1 = 30000;
        //int usersQuantity2 = 300000;
        int iterations = 1000;
        int modellingTime = 10000; //tasks quantity

        Boolean isVariance = false; // with variable parameters of service

        //Dipole (users,providers). min,max.
        SystemsBruteForcer maks1 = new SystemsBruteForcer(true, isVariance,
                new DipoleData(usersQuantity1, providersQuantity), 
                new DipoleData(usersQuantity1, providersQuantity), 
                iterations, modellingTime);
        maks1.run();

        /*
        //Dipole (users,providers). min,max.
        SystemsBruteForcer maks2 = new SystemsBruteForcer(true, isVariance,
                new DipoleData(usersQuantity2, providersQuantity), 
                new DipoleData(usersQuantity2, providersQuantity), 
                iterations, modellingTime);
        maks2.run();
                */
    }

}
