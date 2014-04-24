package servicesystem;

import entities.DipoleData;
import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {
        SystemsBruteForcer maks = new SystemsBruteForcer(true,
                new DipoleData(4, 4), new DipoleData(10, 10), 100, 200);
        maks.run();
    }

}
