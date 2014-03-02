package experiments;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Jama.Matrix;
import experiments.graph.Hystogram;

import exploration.ExplorationStrategy;
import experiments.graph.UniformHystogram;
import messages.ProviderResponse;
import messages.StatisticEntry;
import servicesystem.ServiceSystem;
import util.IO;

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

    /**
     *
     * @param i ID of the experiment
     * @param name experiment name, used for directory name
     * @param strategy exploration strategy
     * @param input experiment input data
     */
    public Experiment(Long i, String name, ExplorationStrategy strategy,
            ExperimentData input) {
        id = i;
        description = name;
        settings = new ExperimentSettings(description);
        data = input;
        explorationStrategy = strategy;
        calculator = new ResultsCounter(input.getIterationsNumber(),
                input.getTasksNumber(), input.getProvidersNumber());
    }

    public void logExperimentData(ProviderResponse pr, Double userEstimate) {
        calculator.addData(iterationCycle, taskNumber, pr, userEstimate);
        taskNumber++;
        statistics.add(new StatisticEntry(pr, userEstimate));
    }

    public void nextIteration() {
        iterationCycle++;
        taskNumber = 0;
    }

    public void printTotalResult()
            throws FileNotFoundException {

        printAllStatistics();
      
        //Average profit
        Matrix total = calculator.getEstimateAverages().transpose();
        IO.printMatrixToFile(total, settings.getResultsFilename(), 1, 3);

        //Hystogram of profits - should be builded on average results
        //  all user estimates - in average vector 'total'
        //  make cute hystogram object  
        makeHystograms(total);
        
        //Providers choosing frequency
        Matrix frequencies = calculator.getProvidersChooseFrequencyAverages().transpose();
        IO.printMatrixToFile(frequencies, settings.getFrequencyFilename(), 1, 3);
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
        //print input data
        try {
            logInputData();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimpleExperiment.class.getName()).log(Level.SEVERE, null, ex);
        }

        //create servicesystem instance
        ServiceSystem system;
        for (int i = 0; i < data.getIterationsNumber(); i++) {
            system = getServiceSystemInstance();
            system.run();
            nextIteration();
        }

        //print output data
        try {
            printTotalResult();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimpleExperiment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int compareTo(Experiment e2) {
        return this.id.compareTo(e2.id);
    }

    protected abstract ServiceSystem getServiceSystemInstance();
}
