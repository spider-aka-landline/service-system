package experiments;

import Jama.Matrix;
import entities.providers.ServiceProvider;
import experiments.graph.Histogram;
import experiments.graph.UniformHistogram;
import exploration.ExplorationStrategy;
import io.IO;
import messages.ProviderResponse;
import reputationsystem.DataEntity;
import servicesystem.ServiceSystem;
import servicesystem.ServiceSystemState;
import strategies.Strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Experiment implements Comparable<Experiment> {

    private final Long id;
    private final String description;

    private Integer iterationCycle = 0;
    private Integer taskNumber = 0;

    protected final ExperimentData data;
    protected final ExperimentSettings settings;
    protected final ExplorationStrategy explorationStrategy;

    protected final StatisticsModule statisticsModule;

    private Long relaxTime;

    /**
     * @param i        ID of the experiment
     * @param name     experiment name, used for directory name
     * @param strategy exploration strategy
     * @param input    experiment input data
     */
    public Experiment(Long i, String name, ExplorationStrategy strategy,
                      ExperimentData input) {
        id = i;
        description = name;
        settings = new ExperimentSettings(description);
        data = input;
        explorationStrategy = strategy;
        statisticsModule = new StatisticsModule(input);
    }

    public void logExperimentData(ProviderResponse pr, Double userEstimate) {
        statisticsModule.addData(iterationCycle, taskNumber, pr, userEstimate);
        taskNumber++;
    }

    public void logExperimentData(long criteriaCompletionTime) {
        statisticsModule.addData(iterationCycle, criteriaCompletionTime);

    }

    public void logExperimentData(ServiceSystemState state) {
        Map<ServiceProvider, DataEntity>
                serviceProviderDataEntityMap = state.getProvidersReputations().getAllProvidersData();
        statisticsModule.addData(iterationCycle, taskNumber, serviceProviderDataEntityMap);
    }

    public void nextIteration() {
        iterationCycle++;
        taskNumber = 0;
    }

    private void printRelaxTime() {
        StringBuilder filepath = new StringBuilder();
        filepath.append(IO.getFilePath(settings.getTimeFilename(), false));

        //Строка для записи в файл
        StringBuilder temp = new StringBuilder();
        temp.append(data.getUsersNumber()).append(",");
        temp.append(data.getProvidersNumber()).append(",");
        temp.append(relaxTime);

        //Изменить имя файла в зависимости от стратегии
        filepath.append(getExperimentStrategy().getClass().getName());
        filepath.append(".txt");

        //Добавление строки в файл (один из, по количесту стратегий)        
        IO.printAdd(temp, filepath.toString());
    }

    public void printTotalResult()
            throws IOException, InterruptedException {

        printRelaxTime();

        printAllStatistics();

        //Average profit
        Matrix total = statisticsModule.getEstimateAverages();
        IO.printMatrixToFile(total, settings.getResultsFilename()); //1,3

        //Average criteria completion time
        Matrix criteriaTime =
                statisticsModule.getCriteriaCompletionTimeAverages();
        String testPath = settings.getCriteriaFilename();
        IO.printMatrixToFile(criteriaTime, testPath);

        //Average reputations for providers
        Map<ServiceProvider, Matrix> reputations = statisticsModule.getProvidersReputationProgressAverages();
        //FIXME
        IO.printReputationsToFile(reputations, settings.getReputationsFilename()); //1,6

        //Histogram of profits - should be built on average results
        //  all user estimates - in average vector 'total'
        //  make cute histogram object
        makeHistograms(total);

        //Providers choosing frequency
        Matrix frequencies
                = statisticsModule.getProvidersChooseFrequencyAverages();
        IO.printMatrixToFile(frequencies, settings.getFrequencyFilename()); //1,3

        //plotting via gnuplot script
        runAllGnuplotScripts();
    }

    private void runAllGnuplotScripts() throws IOException, InterruptedException {
        List<String> names = new ArrayList<>();
        names.add("h.plot");
        names.add("a.plot");
        names.add("a1.plot");
        for (String s : names) {
            runGnuplotScript(s);
        }
    }

    private void runGnuplotScript(String filename) throws IOException, InterruptedException {
        String destinationDir = IO.getFilePath(description);

        ProcessBuilder pb = new ProcessBuilder("gnuplot", filename);
        pb.directory(new File(destinationDir));
        Process p = pb.start();
        p.waitFor();
    }

    //All statistics
    private void printAllStatistics()
            throws FileNotFoundException {
        statisticsModule.printAllStatistics(settings.getStatisticsFilename(), settings.getStateStatisticsFilename());
    }

    private void makeHistograms(Matrix vector)
            throws FileNotFoundException {
        printHistogram(new Histogram(vector));
        printHistogram(new UniformHistogram(vector));
    }

    private void printHistogram(Histogram h) throws FileNotFoundException {
        IO.printHystogramToFile(h,
                IO.getFilePath(settings.getHistogramFilename()));
    }

    private void printHistogram(UniformHistogram uh)
            throws FileNotFoundException {
        IO.printHystogramToFile(uh,
                IO.getFilePath(settings.getUniformHistogramFilename()));
    }

    public void logInputData() throws FileNotFoundException {
        IO.logExperimentInitData(data, settings);
    }

    public void run() {
        relaxTime = 0L;
        //create service system instance
        ServiceSystem system;
        for (int i = 0; i < data.getIterationsNumber(); i++) {
            system = getServiceSystemInstance();
            system.run();
            relaxTime += system.getvalidationResults();
            nextIteration();
        }
        relaxTime /= data.getIterationsNumber();
    }


    @Override
    public int compareTo(Experiment e2) {
        return this.id.compareTo(e2.id);
    }

    protected abstract ServiceSystem getServiceSystemInstance();

    protected abstract Strategy getExperimentStrategy();

}
