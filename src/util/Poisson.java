package util;

import java.util.Random;

public class Poisson {

    private double lambda;
    private Random rand;
	
        /** Creates a variable with a given mean. */
    public Poisson(double lambda) {
        this.lambda = lambda;
        rand = new Random();
    }

    public int next() {
        double product = 0;
        int count =  0;
        int result = 0;
        while(product < 1.0) {
            product -= Math.log(rand.nextDouble()) / lambda;
            result = count;
            count++; // keep result one behind
        }
        return result;
    }

    public static final void test_main() {
        int size = 20;

        Poisson test = new Poisson(2.5 * 1000);
        double total = 0.0;

        for(int line = 0; line < size; ++line) {
            for(int col = 0; col < size; ++col) {
                double next = (double)test.next() / 1000;
                total += next;
                System.out.printf("%2.4f ", next);
            }
            System.out.println();
        }
        System.out.printf(
                "%nThe actual mean arrival time is %.4f%n",
                total / (size * size));
    }
}
