package experiments;

public class ExperimentSettings {

    private final StringBuilder usersFilename = new StringBuilder();
    private final StringBuilder providersFilename = new StringBuilder();
    private final StringBuilder tasksFilename = new StringBuilder();

    private final StringBuilder resultsFilename = new StringBuilder();

    Integer ITERATIONS_NUMBER = 1;

    Integer USERS_NUMBER = 2;
    Integer PROVIDERS_NUMBER = 4;
    Integer TASKS_NUMBER = 50;

    ExperimentSettings(String name) {
        usersFilename.append(name).append("/users.txt");
        providersFilename.append(name).append("/providers.txt");
        tasksFilename.append(name).append("/tasks.txt");
        resultsFilename.append(name).append("/results.txt");
    }

    public String getUsersFilename() {
        return usersFilename.toString();
    }

    public String getProvidersFilename() {
        return usersFilename.toString();
    }

    public String getTasksFilename() {
        return usersFilename.toString();
    }

    public String getResultsFilename() {
        return resultsFilename.toString();
    }
}
