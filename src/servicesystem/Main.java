package servicesystem;

import entities.DipoleData;
import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {
        //Dipole (users,providers). min,max.
        SystemsBruteForcer maks = new SystemsBruteForcer(true,
                new DipoleData(5, 1), new DipoleData(10000, 10000), 100, 200);
        maks.run();
    }

}
