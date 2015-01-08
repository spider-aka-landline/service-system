package experiments;

import Jama.Matrix;
import entities.providers.ServiceProvider;
import experiments.graph.Hystogram;
import experiments.graph.UniformHystogram;
import exploration.ExplorationStrategy;
import messages.ProviderResponse;
import messages.StatisticEntry;
import myutil.IO;
import reputationsystem.DataEntity;
import servicesystem.ServiceSystem;
import servicesystem.ServiceSystemState;
import strategies.Strategy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Experiment implements Comparable<Experiment> {

    private final Long id;
    private final String description;
    private final ResultsCounter calculator;

    private Integer iterationCycle = 0;
    private Integer taskNumber = 0;

    protected final ExperimentData data;
    protected final ExperimentSettings settings;
    protected final ExplorationStrategy explorationStrategy;

    protected final List<StatisticEntry> statistics = new LinkedList<>();
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
        calculator = new ResultsCounter(input.getIterationsNumber(),
                input.getTasksNumber(), input.getProviders());
    }

    public void logExperimentData(ProviderResponse pr, Double userEstimate) {
        calculator.addData(iterationCycle, taskNumber, pr, userEstimate);
        taskNumber++;
        statistics.add(new StatisticEntry(pr, userEstimate));
    }

    public void logExperimentData(long criteriaCompletionTime) {
        calculator.addData(iterationCycle, criteriaCompletionTime);

    }

    public void logExperimentData(ServiceSystemState state) {
        Map<ServiceProvider, DataEntity>
                serviceProviderDataEntityMap = state.getProvidersReputations().getAllProvidersData();
        calculator.addData(iterationCycle, taskNumber, serviceProviderDataEntityMap);
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
        temp.append(data.getUsersNumber()).append(" ");
        temp.append(data.getProvidersNumber()).append(" ");
        temp.append(relaxTime);

        //Изменить имя файла в зависимости от стратегии
        filepath.append(getExperimentStrategy().getClass().getName());
        filepath.append(".txt");

        //Добавление строки в файл (один из, по количесту стратегий)        
        IO.printAdd(temp, filepath.toString());
    }

    public void printTotalResult()
            throws FileNotFoundException, IOException {

        printRelaxTime();

        printAllStatistics();

        //Average profit
        Matrix total = calculator.getEstimateAverages();
        IO.printMatrixToFile(total, settings.getResultsFilename(), 1, 3);

        //Average criteria completion time
        Matrix criteriaTime =
                calculator.getCriteriaCompletionTimeAverages();
        String testPath = settings.getCriteriaFilename();
        IO.printMatrixToFile(criteriaTime, testPath, 1, 6);

        //Average reputations for providers
        Map<ServiceProvider, Matrix> reputations  = calculator.getProvidersReputationProgressAverages();
        //FIXME
        IO.printReputationsToFile(reputations,settings.getReputationsFilename());

        //Hystogram of profits - should be builded on average results
        //  all user estimates - in average vector 'total'
        //  make cute hystogram object  
        makeHystograms(total);

        //Providers choosing frequency
        Matrix frequencies
                = calculator.getProvidersChooseFrequencyAverages();
        IO.printMatrixToFile(frequencies, settings.getFrequencyFilename(), 1, 3);

        //plotting via gnuplot script
        runAllGnuplotScripts();
    }

    private void runAllGnuplotScripts() throws IOException {
        List<String> names = new ArrayList<>();
        names.add("h.plot");
        names.add("a.plot");
        names.add("a1.plot");
        for (String s : names) {
            runGnuplotScript(s);
        }
    }

    private void runGnuplotScript(String filename) throws IOException {
        Process p = new ProcessBuilder("gnuplot",
                IO.getFilePath(description + "/" + filename)).start();
        System.out.println(IO.getFilePath(description + "/" + filename));
    }

    //All statistics
    private void printAllStatistics()
            throws FileNotFoundException {
        IO.printCollection(statistics,
                IO.getFilePath(settings.getStatisticsFilename()));
    }

    private void makeHystograms(Matrix vector)
            throws FileNotFoundException {
        printHystogram(new Hystogram(vector));
        printHystogram(new UniformHystogram(vector));
    }

    private void printHystogram(Hystogram h) throws FileNotFoundException {
        IO.printHystogramToFile(h,
                IO.getFilePath(settings.getHystogramFilename()));
    }

    private void printHystogram(UniformHystogram uh)
            throws FileNotFoundException {
        IO.printHystogramToFile(uh,
                IO.getFilePath(settings.getUniformHystogramFilename()));
    }

    public void logInputData() throws FileNotFoundException {
        IO.logExperimentInitData(data, settings);
    }

    public void run() {
        relaxTime = 0L;
        //create servicesystem instance
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
