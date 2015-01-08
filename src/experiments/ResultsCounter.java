package experiments;

import Jama.Matrix;
import entities.providers.ServiceProvider;
import messages.ProviderResponse;
import reputationsystem.DataEntity;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResultsCounter {

    private final Matrix criteriaCompletionTimeMatrix;
    private final Matrix results;
    private final Matrix providerCount; //for frequencies. not in use now.
    private final Map<ServiceProvider, Matrix> providersReputationProgress;

    private final int iterationsQuantity;
    private final int tasksQuantity;

    public ResultsCounter(Integer iterations, Integer tasks, Collection<ServiceProvider> allProviders) {
        criteriaCompletionTimeMatrix = new Matrix(iterations, 1);
        results = new Matrix(iterations, tasks);
        providerCount = new Matrix(iterations, allProviders.size());

        providersReputationProgress = new TreeMap<>();
        for (ServiceProvider key : allProviders) {
            providersReputationProgress.put(key, new Matrix(iterations, tasks, 0.));
        }
        iterationsQuantity = iterations;
        tasksQuantity = tasks;
    }

    public Matrix getEstimateAverages() {
        return getAverages(results).transpose();
    }

    public Matrix getProvidersChooseFrequencyAverages() {
        return getAverages(providerCount).transpose();
    }

    public Matrix getCriteriaCompletionTimeAverages() {
        return getAverages(criteriaCompletionTimeMatrix).transpose();
    }

    public Map<ServiceProvider, Matrix> getProvidersReputationProgressAverages() {
        return getAverages(providersReputationProgress);
    }

    private <K> Map<K, Matrix> getAverages(Map<K, Matrix> providersReputationProgress) {
        Map<K, Matrix> resultMap = new TreeMap<>();

        Iterator<Map.Entry<K, Matrix>> iterator = providersReputationProgress.entrySet().iterator();
        Map.Entry<K, Matrix> currentEntry;
        K currentKey;
        Matrix currentValue;

        while (iterator.hasNext()) {
            currentEntry = iterator.next();
            currentKey = currentEntry.getKey();
            currentValue = currentEntry.getValue();
            //providersReputationProgress
            resultMap.put(currentKey, getAverages(currentValue));
        }

        return resultMap;
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
            return new Matrix(1, 1);
        }

    }

    void addData(Integer iterationCycle, Integer taskNumber,
                 ProviderResponse pr, Double value) {
        results.set(iterationCycle, taskNumber, value);
        //updateProviderCount(iterationCycle, pr.getServiceProviderId());
    }

    void addData(Integer iterationCycle, long time) {
        criteriaCompletionTimeMatrix.set(iterationCycle, 0, time);
    }

    void addData(Integer iterationCycle, Integer taskNumber,
                 Map<ServiceProvider, DataEntity> serviceProviderDataEntityMap) {
        Iterator<Map.Entry<ServiceProvider, DataEntity>> iterator = serviceProviderDataEntityMap.entrySet().iterator();
        Map.Entry<ServiceProvider, DataEntity> currentEntry;
        ServiceProvider currentKey;
        while (iterator.hasNext()) {
            currentEntry = iterator.next();
            currentKey = currentEntry.getKey();
            Double currentReputation = currentEntry.getValue().getReputation();

            if (!providersReputationProgress.containsKey(currentKey))
                providersReputationProgress.put(currentKey, new Matrix(iterationsQuantity, tasksQuantity, 0.));
            Matrix currentReputationMatrix = providersReputationProgress.get(currentKey);
            currentReputationMatrix.set(iterationCycle, taskNumber-1, currentReputation);
        }

    }

    private void updateProviderCount(Integer iterationCycle,
                                     int serviceProviderId) {

        try {
            double temp = providerCount.get(iterationCycle, serviceProviderId);
            providerCount.set(iterationCycle, serviceProviderId, ++temp);

        } catch (ArrayIndexOutOfBoundsException ex) {
            StringBuilder sb = new StringBuilder();
            sb.append("at iterationCycle = ");
            sb.append(iterationCycle);
            sb.append(", serviceProvider = ");
            sb.append(serviceProviderId);
            sb.append(", providerCount [");
            sb.append(providerCount.getRowDimension());
            sb.append("x");
            sb.append(providerCount.getColumnDimension());
            sb.append("]");

            Logger.getLogger(ResultsCounter.class.getName()).log(Level.SEVERE,
                    sb.toString(), ex);
        }
    }

}
