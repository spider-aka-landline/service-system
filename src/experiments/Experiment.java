package experiments;

import java.io.FileNotFoundException;
import Jama.Matrix;
import exploration.ExplorationStrategy;
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
                input.getTasksNumber());
    }

    public void logExperimentData(Double value) {
        calculator.addData(iterationCycle, taskNumber, value);
        taskNumber++;
    }

    public void nextIteration() {
        iterationCycle++;
        taskNumber = 0;
        /*if (debug) {
         out.format("=== Iteration %d === %n", iterationCycle);
         }*/
    }

    public void printTotalResult()
            throws FileNotFoundException {

        Matrix total = calculator.getAverages().transpose();
        IO.printMatrixToFile(total, settings.getResultsFilename(), 1, 3);
    }

    public void logInputData() throws FileNotFoundException {
        IO.logExperimentInitData(data, settings);
    }

    public abstract void run();

    @Override
    public int compareTo(Experiment e2) {
        return this.id.compareTo(e2.id);
    }

    
}
