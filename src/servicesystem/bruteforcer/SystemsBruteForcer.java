package servicesystem.bruteforcer;

import entities.DipoleData;
import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;
import experiments.Experiment;
import experiments.generators.ExperimentsGenerator;
import io.IO;
import servicesystem.ExperimentsRunner;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SystemsBruteForcer {

    private final Boolean generateWithVariance;

    private final Boolean generateInitData;

    private final Integer iterationsNumber;
    private final Integer tasksNumber;

    BruteForcerData data;

    ExperimentsGenerator experimentsGenerator;

    // with generation all data only

    /**
     * @param generate        Boolean - if data should be generatedDataForExperiments
     *                        or readed
     * @param dipoles         Collection<DipoleData>
     * @param iterationsCount how many iterations
     * @param tasksCount      how many tasks
     */
    public SystemsBruteForcer(Boolean generate, Boolean hasVariance, Collection<DipoleData> dipoles,
                              Integer iterationsCount, Integer tasksCount, ExperimentsGenerator generator) {
        generateInitData = generate;
        generateWithVariance = hasVariance;

        iterationsNumber = iterationsCount;
        tasksNumber = tasksCount;
        experimentsGenerator = generator;

        if (generateInitData) {
            BruteForcerDataGenerator bruteDataGen
                    = BruteForcerDataGenerator.getInstance();
            data = bruteDataGen.generateData(hasVariance, dipoles);
        } else {
            try {
                data = BruteForcerDataReader.readData(dipoles);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() throws InterruptedException {
        Set<DipoleData> numbers = data.getNumbers();
        for (DipoleData d : numbers) {
            runExperimentPlan(d);
        }
    }


    private void runExperimentPlan(DipoleData d) throws InterruptedException {
        StringBuilder currentNumbers = new StringBuilder();
        currentNumbers.append("u").append(d.getUserNumber())
                .append("p").append(d.getProviderNumber())
                .append("/");
        IO.setAppendix(currentNumbers.toString());

        //get data for all experiments
        Collection<User> currentUsers
                = data.getUsers(d.getUserNumber());
        Collection<ServiceProvider> currentProviders
                = data.getProviders(d.getProviderNumber());

        List<Experiment> currentExperimentPlan = getExperimentPlan(currentUsers, currentProviders);
        ExperimentsRunner runner = new ExperimentsRunner(currentExperimentPlan);
        runner.run();
    }

    private List<Experiment> getExperimentPlan(Collection<User> currentUsers, Collection<ServiceProvider> currentProviders) {
        if (generateInitData) {
            return experimentsGenerator.createExperimentPlan(currentUsers, currentProviders,
                    tasksNumber, iterationsNumber, generateWithVariance);
        } else {
            Collection<Task> currentTasks = BruteForcerDataReader.readTasks(currentUsers, tasksNumber);

            return experimentsGenerator.createExperimentPlan(currentUsers, currentProviders,
                    currentTasks, iterationsNumber);
        }
    }
}
