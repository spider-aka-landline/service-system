package experiments;

public class ExperimentSettings {

    private final StringBuilder usersFilename = new StringBuilder();
    private final StringBuilder providersFilename = new StringBuilder();
    private final StringBuilder tasksFilename = new StringBuilder();

    private final StringBuilder statisticsFilename = new StringBuilder();
    private final StringBuilder resultsFilename = new StringBuilder();
    private final StringBuilder frequenciesFilename = new StringBuilder();
    
    private final StringBuilder hystogramFilename
            = new StringBuilder();
    private final StringBuilder uniformHystogramFilename
            = new StringBuilder();
    
    public ExperimentSettings(String name) {
        usersFilename.append(name).append("/users.txt");
        providersFilename.append(name).append("/providers.txt");
        tasksFilename.append(name).append("/tasks.txt");
        statisticsFilename.append(name).append("/statistics.txt");
        resultsFilename.append(name).append("/results.txt");
        frequenciesFilename.append(name).append("/frequencies.txt");
        hystogramFilename.append(name).append("/hystogram.txt");
        uniformHystogramFilename.append(name).append("/hystogram2.txt");
    }

    public String getUsersFilename() {
        return usersFilename.toString();
    }

    public String getProvidersFilename() {
        return providersFilename.toString();
    }

    public String getTasksFilename() {
        return tasksFilename.toString();
    }

    public String getResultsFilename() {
        return resultsFilename.toString();
    }

    public String getStatisticsFilename() {
        return statisticsFilename.toString();
    }
    
    public String getHystogramFilename() {
        return hystogramFilename.toString();
    }

    public String getUniformHystogramFilename() {
        return uniformHystogramFilename.toString();
    }

    String getFrequencyFilename() {
        return frequenciesFilename.toString();
    }
}
