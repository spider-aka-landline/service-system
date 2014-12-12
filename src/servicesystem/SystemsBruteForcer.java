package servicesystem;

import entities.DipoleData;
import entities.providers.ServiceProvider;
import entities.users.User;
import experiments.Experiment;
import experiments.generators.ExperimentsGenerator;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import myutil.IO;
import myutil.generators.BruteForcerDataGenerator;

public class SystemsBruteForcer {

    private final Boolean generateWithVariance;

    private final Boolean generateInitData;

    private final Integer iterationsNumber;
    private final Integer tasksNumber;

    BruteForcerData data;

    ExperimentsGenerator experimentsGenerator;

    // with generation all data only
    /**
     *
     * @param generate Boolean - if data should be generatedDataForExperiments
     * or readed
     * @param minimum DipoleData (users, providers)
     * @param maximum DipoleData (users, providers)
     * @param iterationsCount how many iterations
     * @param tasksCount how many tasks
     */
    SystemsBruteForcer(Boolean generate, Boolean hasVariance, DipoleData minimum, DipoleData maximum,
            Integer iterationsCount, Integer tasksCount, ExperimentsGenerator generator) {
        generateInitData = generate;
        generateWithVariance = hasVariance;

        iterationsNumber = iterationsCount;
        tasksNumber = tasksCount;
        experimentsGenerator = generator;

        if (generateInitData) {
            BruteForcerDataGenerator bruteDataGen
                    = BruteForcerDataGenerator.getInstance();
            data = bruteDataGen.generateData(hasVariance, minimum, maximum);
        } else {
            throw new UnsupportedOperationException("Reading: not supported yet.");
        }
    }

    public void run() {
        Set<DipoleData> numbers = data.getNumbers();
        for (DipoleData d : numbers) {
            runExperimentPlan(d);
        }
    }

    private void runExperimentPlan(DipoleData d) {
        StringBuilder currentNumbers = new StringBuilder();
        currentNumbers.append("u").append(d.getUserNumber());
        currentNumbers.append("p").append(d.getProviderNumber());
        currentNumbers.append("/");
        IO.setAppendix(currentNumbers.toString());

        //Generate data for all experiments
        Collection<User> currentUsers
                = data.getUsers(d.getUserNumber());
        Collection<ServiceProvider> currentProviders
                = data.getProviders(d.getProviderNumber());

        List<Experiment> currentExperimentPlan
                = experimentsGenerator.createExperimentPlan(currentUsers, currentProviders,
                tasksNumber, iterationsNumber, generateWithVariance);
        
        ExperimentsRunner runner = new ExperimentsRunner(currentExperimentPlan);
        runner.run();
    }

}
