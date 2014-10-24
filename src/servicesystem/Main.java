package servicesystem;

import entities.DipoleData;
import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {
        int p = 30;
        int u1 = 30000;
        int u2 = 300000;

        Boolean isVariance = true;

        //Dipole (users,providers). min,max.
        SystemsBruteForcer maks1 = new SystemsBruteForcer(true, isVariance,
                new DipoleData(u1, p), new DipoleData(u1, p), 1000, 1000);
        maks1.run();

        //Dipole (users,providers). min,max.
        SystemsBruteForcer maks2 = new SystemsBruteForcer(true, isVariance,
                new DipoleData(u2, p), new DipoleData(u2, p), 1000, 1000);
        maks2.run();

    }

}
