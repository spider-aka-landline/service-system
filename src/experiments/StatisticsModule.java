package experiments;

import Jama.Matrix;
import entities.providers.ServiceProvider;
import io.IO;
import messages.ProviderResponse;
import messages.StateStatisticEntry;
import messages.StatisticEntry;
import reputationsystem.DataEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StatisticsModule {

    protected final ResultsCounter calculator;

    protected final List<StatisticEntry> statistics = new LinkedList<>();
    protected final List<StateStatisticEntry> stateStatistics = new LinkedList<>();


    public StatisticsModule(ExperimentData input) {
        calculator = new ResultsCounter(input.getIterationsNumber(),
                input.getTasksNumber(), input.getProviders());
    }

    public void addData(Integer iterationCycle, Integer taskNumber, ProviderResponse pr, Double userEstimate) {
        calculator.addData(iterationCycle, taskNumber, pr, userEstimate);
        StatisticEntry statisticEntry = createStatisticEntry(iterationCycle, taskNumber, pr, userEstimate);
        statistics.add(statisticEntry);
    }

    private StatisticEntry createStatisticEntry(Integer iterationCycle, Integer taskNumber,
                                                ProviderResponse pr, Double userEstimate) {
        return new StatisticEntry(iterationCycle, taskNumber, pr, userEstimate);
    }

    public void addData(Integer iterationCycle, long criteriaCompletionTime) {
        calculator.addData(iterationCycle, criteriaCompletionTime);
    }


    public void addData(Integer iterationCycle, Integer taskNumber,
                        Map<ServiceProvider, DataEntity> serviceProviderDataEntityMap) {
        calculator.addData(iterationCycle, taskNumber, serviceProviderDataEntityMap);
        StateStatisticEntry stateStatisticEntry;
        for(Map.Entry<ServiceProvider, DataEntity> entry : serviceProviderDataEntityMap.entrySet())
        {
            stateStatisticEntry = createStateStatisticEntry(iterationCycle, taskNumber, entry);
            stateStatistics.add(stateStatisticEntry);
        }

    }

    private StateStatisticEntry createStateStatisticEntry(Integer iterationCycle, Integer taskNumber, Map.Entry<ServiceProvider, DataEntity> serviceProviderDataEntity) {
        return new StateStatisticEntry(iterationCycle, taskNumber, serviceProviderDataEntity);
    }


    public Matrix getEstimateAverages() {
        return calculator.getEstimateAverages();
    }

    public Matrix getCriteriaCompletionTimeAverages() {
        return calculator.getCriteriaCompletionTimeAverages();
    }

    public Map<ServiceProvider, Matrix> getProvidersReputationProgressAverages() {
        return calculator.getProvidersReputationProgressAverages();
    }

    public Matrix getProvidersChooseFrequencyAverages() {
        return calculator.getProvidersChooseFrequencyAverages();
    }

    public void printAllStatistics(String statisticsFilename, String stateStatisticsFilename) {
        IO.printCollection(statistics, IO.getFilePath(statisticsFilename), StateStatisticEntry.getHeader());
        IO.printCollection(stateStatistics, IO.getFilePath(stateStatisticsFilename), StateStatisticEntry.getHeader());
    }
}
