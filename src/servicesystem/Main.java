package servicesystem;

import entities.DipoleData;

import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {
        int providersQuantity = 30;
        int[] usersQuantity = {30000 /*, 300000 */ };

        for (int users : usersQuantity) {
            //Dipole (users,providers). min,max.
            DipoleData minDipole = new DipoleData(users, providersQuantity);
            SystemsBruteForcer system = getSystemBruteForcer(minDipole, minDipole);
            system.run();
        }

    }

    private static SystemsBruteForcer getSystemBruteForcer(DipoleData min, DipoleData max) {
        boolean generateInitData = true; //currently works with generation only
        boolean isVariance = false; // with variable parameters of service

        int iterations = 1000;
        int modellingTime = 1000; //tasks quantity

        return new SystemsBruteForcer(generateInitData, isVariance,
                min, max, iterations, modellingTime);
    }

}
