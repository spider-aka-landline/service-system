package servicesystem;

import entities.DipoleData;
import entities.Params;
import entities.providers.ServiceProvider;
import entities.users.User;
import experiments.Experiment;
import experiments.ExperimentData;
import experiments.ExperimentDataWithVariance;
import experiments.SimpleExperiment;
import exploration.EpsilonDecreasingStrategy;
import exploration.ExplorationStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import myutil.Generator;
import myutil.GeneratorWithVariance;
import myutil.IO;
import strategies.RLStrategy;
import strategies.RLWithReputationMaxReputationStrategy;
import strategies.RLWithReputationStrategy;
import strategies.RandomStrategy;

public class SystemsBruteForcer {

    private static final Double FIXED_VARIANCE = 0.5;
    private final Boolean generateWithVar;

    private final Boolean generateInitData;

    private final Integer minUsersNumber;
    private final Integer maxUsersNumber;

    private final Integer minProvidersNumber;
    private final Integer maxProvidersNumber;

    private final Integer iterationsNumber;
    private final Integer tasksNumber;

    private SortedSet<DipoleData> numbers = new TreeSet<>();
    private final SortedMap<Integer, Collection<User>> usersBase;
    private final SortedMap<Integer, Collection<ServiceProvider>> providersBase;
    ExperimentData generatedDataForExperiments;

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
    SystemsBruteForcer(Boolean generate, Boolean generateWithVariance, DipoleData minimum, DipoleData maximum,
            Integer iterationsCount, Integer tasksCount) {
        generateInitData = generate;
        generateWithVar = generateWithVariance;
        minUsersNumber = minimum.getUserNumber();
        maxUsersNumber = maximum.getUserNumber();

        minProvidersNumber = minimum.getProviderNumber();
        maxProvidersNumber = maximum.getProviderNumber();

        iterationsNumber = iterationsCount;
        tasksNumber = tasksCount;

        numbers = new TreeSet<>();
        usersBase = new TreeMap<>();
        providersBase = new TreeMap<>();

        //get init data from the Gap Between the Worlds
        if (generateInitData) {
            for (Integer u = minUsersNumber; u <= maxUsersNumber;
                    u += logIncrement(u)) {
                for (Integer p = minProvidersNumber; p <= maxProvidersNumber;
                        p += logIncrement(p)) {
                    numbers.add(new DipoleData(u, p));

                    if (u.equals(minUsersNumber)) {
                        // for all providers
                        Generator gen;
                        if (generateWithVariance) {
                            Params variance = new Params(FIXED_VARIANCE);
                            gen = new GeneratorWithVariance(variance);
                        } else {
                            gen = new Generator();
                        }
                        providersBase.put(p, gen.generateProviders(p));
                    }
                }
                // for all users
                usersBase.put(u, (new Generator()).generateUsers(u));
            }
        } else {
            throw new UnsupportedOperationException("Reading: not supported yet.");
        }
    }

    private static Integer logIncrement(Integer arg) {
        if (arg < 10) {
            return 1;
        } else {
            if (arg % 10 != 0) {
                throw new IllegalArgumentException("Wrong increment input");
            }
            return 10 * logIncrement(arg / 10);
        }
    }

    public void run() {
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
                = getUsers(d.getUserNumber());
        Collection<ServiceProvider> currentProviders
                = getProviders(d.getProviderNumber());

        if (generateWithVar) {
            generatedDataForExperiments = new ExperimentDataWithVariance(currentUsers,
                    currentProviders, tasksNumber, iterationsNumber);
        } else {
            generatedDataForExperiments = new ExperimentData(currentUsers,
                    currentProviders, tasksNumber, iterationsNumber);
        }

        List<Experiment> currentExperimentPlan
                = createExperimentPlan(generatedDataForExperiments);
        ExperimentsRunner runner = new ExperimentsRunner(currentExperimentPlan);
        runner.run();
    }

    private List<Experiment> createExperimentPlan(ExperimentData expData) {
        //Only one exploration strategy;
        ExplorationStrategy strategy = new EpsilonDecreasingStrategy();

        //container for all experiments
        List<Experiment> exps = new ArrayList<>();

        String currentExperimentBlockName;
        if (generateWithVar) {
            currentExperimentBlockName = "const-with-variances";
        } else {
            currentExperimentBlockName = "simple-constants";
        }

        //First experiment: random
        String name01 = "random";
        StringBuilder path01 = new StringBuilder();
        path01.append(currentExperimentBlockName).append("/").append(name01);
        
        Experiment exp01 = new SimpleExperiment(Long.valueOf(1),
                path01.toString(), strategy,
                new RandomStrategy(), expData);

        exps.add(exp01);

        //Second experiment: RL, e-decreasing
        String name02 = "rl";
        StringBuilder path02 = new StringBuilder();
        path02.append(currentExperimentBlockName).append("/").append(name02);
        
        Experiment exp02 = new SimpleExperiment(Long.valueOf(2),
                path02.toString(), strategy,
                new RLStrategy(), expData);

        exps.add(exp02);

        //Third experiment: RL, e-decreasing, reputation
        String name03 = "reputationV";
        StringBuilder path03 = new StringBuilder();
        path03.append(currentExperimentBlockName).append("/").append(name03);
        
        Experiment exp03 = new SimpleExperiment(Long.valueOf(3),
<<<<<<< HEAD
                path03.toString(), strategy,
=======
                "simple-constants/reputationV", strategy,
>>>>>>> bd7ead029752b4fcb4cdf8a5d51791baa7e0947d
                new RLWithReputationStrategy(), expData);

        exps.add(exp03);
        
        //Third experiment: RL, e-decreasing, reputation (max reputation)
        Experiment exp04 = new SimpleExperiment(Long.valueOf(4),
                "simple-constants/reputationR", strategy,
                new RLWithReputationMaxReputationStrategy(), expData);

        exps.add(exp04);

        //Third experiment: RL, e-decreasing, reputation (max reputation)
        String name04 = "reputationR";
        StringBuilder path04 = new StringBuilder();
        path04.append(currentExperimentBlockName).append("/").append(name04);
        
        Experiment exp04 = new SimpleExperiment(Long.valueOf(4),
                path04.toString(), strategy,
                new RLWithReputationMaxReputationStrategy(), expData);

        exps.add(exp04);

        return exps;
    }

    private Collection<User> getUsers(Integer usersNumber) {
        if (!usersBase.containsKey(usersNumber)) {
            throw new IllegalArgumentException();
        }
        return usersBase.get(usersNumber);
    }

    private Collection<ServiceProvider> getProviders(Integer providersNumber) {
        if (!providersBase.containsKey(providersNumber)) {
            System.err.println(providersBase);
            throw new IllegalArgumentException();
        }
        return providersBase.get(providersNumber);
    }

}
