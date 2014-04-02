package experiments;

import Jama.Matrix;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.ProviderResponse;

public class ResultsCounter {

    private final Matrix results;
    private final Matrix providerCount;

    public ResultsCounter(Integer iterations, Integer tasks, Integer providers) {
        results = new Matrix(iterations, tasks);
        providerCount = new Matrix(iterations, providers);
    }

    public Matrix getEstimateAverages() {
        return getAverages(results);
    }

    public Matrix getProvidersChooseFrequencyAverages() {
        return getAverages(providerCount);
    }

    private Matrix getAverages(Matrix input) {
        try {
            Integer x = input.getRowDimension();
            Integer y = input.getColumnDimension();
            Matrix ones = new Matrix(1, x, 1.0);
            Matrix zeros = new Matrix(y, 1);

            Matrix temp = new Matrix(1, y);

            for (int i = 0; i < y; i++) {
                zeros.set(i, 0, 1.0);
                temp.set(0, i, (ones.times(input.times(zeros))).det() / x);

                zeros.set(i, 0, 0.0);
            }
            return temp;
        } catch (ArithmeticException e) {
            System.err.println("while getAverage");
            return new Matrix(1,1);
        }
    }

    void addData(Integer iterationCycle, Integer taskNumber,
            ProviderResponse pr, Double value) {
        results.set(iterationCycle, taskNumber, value);
        updateProviderCount(iterationCycle, pr.getServiceProviderId());
    }

    private void updateProviderCount(Integer iterationCycle, int serviceProviderId) {
        
        try {
            double temp = providerCount.get(iterationCycle, serviceProviderId - 1);
            providerCount.set(iterationCycle, serviceProviderId - 1, ++temp);
        } catch (ArrayIndexOutOfBoundsException ex) {
            StringBuilder sb = new StringBuilder();
            sb.append("at iterationCycle = ");
            sb.append(iterationCycle);
            sb.append(", serviceProvider = "); 
            sb.append(serviceProviderId);
            
            Logger.getLogger(ResultsCounter.class.getName()).log(Level.SEVERE, 
                    sb.toString(), ex);
        }
    }

}
