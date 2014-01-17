package experiments;

import java.io.FileNotFoundException;
import Jama.Matrix;
import servicesystem.ServiceSystem;
import util.IO;

public abstract class Experiment implements Comparable<Experiment> {

    private final Long id;
    private final String description;
    private final ResultsCounter calculator;

    //private final ServiceSystem system;
    
    private Integer iterationCycle = 0;
    private Integer taskNumber = 0;
    

    public Experiment(Long i, String name, Integer iterations, Integer tasks) {
        id = i;
        description = name;
        calculator = new ResultsCounter(iterations, tasks);

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

    public void printTotalResult(String fileName)
            throws FileNotFoundException {

        Matrix total = calculator.getAverages().transpose();
        IO.printMatrixToFile(total, fileName, 1, 3);
    }

    public abstract void run();

    @Override
    public int compareTo(Experiment e2) {
        return this.id.compareTo(e2.id);
    }

    public void printTotalResult() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
