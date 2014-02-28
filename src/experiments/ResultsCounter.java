package experiments;

import Jama.Matrix;
import messages.ProviderResponse;

public class ResultsCounter {

    private final Integer x, y;
    private final Matrix results;
    private final Matrix providerCount;

    private final Matrix ones;
    private final Matrix zeros;
    

    public ResultsCounter(Integer iterations, Integer tasks, Integer providers) {
        x = iterations;
        y = tasks;
        results = new Matrix(x, y);
        ones = new Matrix(1, results.getRowDimension(), 1.0);
        zeros = new Matrix(results.getColumnDimension(), 1);
        providerCount = new Matrix(x, providers);
    }

    public Matrix getAverages() {
        Matrix temp = new Matrix(1, y);

        for (int i = 0; i < y; i++) {
            zeros.set(i, 0, 1.0);
            temp.set(0, i, (ones.times(results.times(zeros))).det() / x);

            zeros.set(i, 0, 0.0);
        }
        return temp;
    }

    void addData(Integer iterationCycle, Integer taskNumber,
            ProviderResponse pr, Double value) {
        results.set(iterationCycle, taskNumber, value);
        updateProviderCount(iterationCycle, pr.getServiceProviderId());
    }

    private void updateProviderCount(Integer iterationCycle, int serviceProviderId) {
        try{
        double temp = providerCount.get(iterationCycle, serviceProviderId - 1);
        providerCount.set(iterationCycle, serviceProviderId - 1, ++temp);
        } catch (ArrayIndexOutOfBoundsException e){
            System.err.println("at iterationCycle = ");
            System.err.print(iterationCycle);
            System.err.println("at serviceProvider = ");
            System.err.print(serviceProviderId);
        }
    }

}
