package math;

import java.util.Random;

public class Poisson {

    private final Double lambda;
    private final Random rand = new Random();
	
    /** Creates a variable with a given mean.
     * @param lambda1 */
    public Poisson(Double lambda1) {
        lambda = lambda1;
    }

    public Integer next() {
        Double product = 0.0;
        Integer count =  0;
        Integer result = 0;
        while(product < 1.0) {
            product -= Math.log(rand.nextDouble()) / lambda;
            result = count;
            count++; // keep result one behind
        }
        return result;
    }

    //TODO: remove this. old "testing" method from another class
    public static final void testMain() {
        Integer size = 20;

        Poisson test = new Poisson(2.5 * 1000);
        Double total = 0.0;

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
