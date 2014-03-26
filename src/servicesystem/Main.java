package servicesystem;

import entities.DipoleData;
import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {
        SystemsBruteForcer maks = new SystemsBruteForcer(true,
                new DipoleData(1, 1), new DipoleData(10000, 10000), 100, 5000);
        maks.run();
    }

}
