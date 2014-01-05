package math;

public class StdRandom {

    /**
     * @return real number uniformly in [0, 1).
     */
    public static double uniform() {
        return Math.random();
    }

    /**
     * @param a
     * @param b
     * @return real number uniformly in [a, b).
     */
    public static double uniform(double a, double b) {
        return a + Math.random() * (b-a);
    }

    /**
     * @param N
     * @return an integer uniformly between 0 and N-1.
     */
    public static int uniform(int N) {
        return (int) (Math.random() * N);
    }

    /**
     * @param p
     * @return a boolean, which is true with probability p, and false otherwise.
     */
    public static boolean bernoulli(double p) {
        return Math.random() < p;
    }

    /**
     * @return a boolean, which is true with probability .5, and false otherwise.
     */
    public static boolean bernoulli() {
        return bernoulli(0.5);
    }

    /**
     * @return a real number with a standard Gaussian distribution.
     */
    public static double gaussian() {
        // use the polar form of the Box-Muller transform
        double r, x, y;
        do {
            x = uniform(-1.0, 1.0);
            y = uniform(-1.0, 1.0);
            r = x*x + y*y;
        } while (r >= 1 || r == 0);
        return x * Math.sqrt(-2 * Math.log(r) / r);

        // Remark:  y * Math.sqrt(-2 * Math.log(r) / r)
        // is an independent random gaussian
    }

    /**
     * @param mean
     * @param stddev
     * @return a real number from a gaussian distribution with given mean and stddev
     */
    public static double gaussian(double mean, double stddev) {
        return mean + stddev * gaussian();
    }

    /**
     * @return an integer with a geometric distribution with mean 1/p.
     */
    public static int geometric(double p) {
        // using algorithm given by Knuth
        return (int) Math.ceil(Math.log(uniform()) / Math.log(1.0 - p));
    }

    /**
     * @return an integer with a Poisson distribution with mean lambda.
     */
    public static int poisson(double lambda) {
        // using algorithm given by Knuth
        // see http://en.wikipedia.org/wiki/Poisson_distribution
        int k = 0;
        double p = 1.0;
        double L = Math.exp(-lambda);
        do {
            k++;
            p *= uniform();
        } while (p >= L);
        return k-1;
    }

    /**
     * @param alpha
     * @return a real number with a Pareto distribution with parameter alpha.
     */
    public static double pareto(double alpha) {
        return Math.pow(1 - uniform(), -1.0/alpha) - 1.0;
    }

    public static double pareto(double alpha, double beta) {
        return beta*Math.pow(1 - uniform(), -1.0/alpha);
    }

    /**
     * @return a real number with a Cauchy distribution.
     */
    public static double cauchy() {
        return Math.tan(Math.PI * (uniform() - 0.5));
    }

    /**
     * @param a
     * @return a number from a discrete distribution: i with probability a[i].
     */
    public static int discrete(double[] a) {
        // precondition: sum of array entries equals 1
        double r = Math.random();
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum = sum + a[i];
            if (sum >= r) return i;
        }
        assert (false);
        return -1;
    }

    /**
     * @param lambda
     * @return a real number from an exponential distribution with rate lambda.
     */
    public static double exp(double lambda) {
        return -Math.log(1 - Math.random()) / lambda;
    }
    
}
