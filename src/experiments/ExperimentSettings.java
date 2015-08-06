package experiments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExperimentSettings {

    static final String EXTENSION = "csv";
    private final String experimentName;
    private final StringBuilder timeFilename = new StringBuilder();
    public ExperimentSettings(String experiment) {
        experimentName = experiment;
        timeFilename.append("/time-");
    }

    public String getTimeFilename() {
        return timeFilename.toString();
    }

    public String getUsersFilename() {
        return OutputFiles.USERS.getFilename(experimentName);
    }

    public String getProvidersFilename() {
        return OutputFiles.PROVIDERS.getFilename(experimentName);
    }

    public String getTasksFilename() {
        return OutputFiles.TASKS.getFilename(experimentName);
    }

    public String getResultsFilename() {
        return OutputFiles.RESULTS.getFilename(experimentName);
    }

    public String getStatisticsFilename() {
        return OutputFiles.STATISTICS.getFilename(experimentName);
    }

    public String getStateStatisticsFilename() {
        return OutputFiles.STATESTATISTICS.getFilename(experimentName);
    }

    public String getHistogramFilename() {
        return OutputFiles.HISTOGRAM.getFilename(experimentName);
    }

    public String getUniformHistogramFilename() {
        return OutputFiles.UNIFORMHISTOGRAM.getFilename(experimentName);
    }

    String getFrequencyFilename() {
        return OutputFiles.FREQUENCIES.getFilename(experimentName);
    }

    String getCriteriaFilename() {
        return OutputFiles.CRITERIA.getFilename(experimentName);
    }

    public String getReputationsFilename() {
        return OutputFiles.REPUTATIONS.getFilename(experimentName);
    }

    enum OutputFiles {
        USERS, PROVIDERS, TASKS, STATISTICS, STATESTATISTICS,
        RESULTS, CRITERIA, FREQUENCIES, HISTOGRAM, UNIFORMHISTOGRAM, REPUTATIONS;

        StringBuilder sb = new StringBuilder();
        static Date date = Calendar.getInstance().getTime();

        static String dateFormatted = getFormattedDate();

        static String getFormattedDate(){
            SimpleDateFormat sdf = new SimpleDateFormat("MMdd-hhmm");
            return sdf.format(date);
        }

        public String getFilename(String experiment) {
            sb.append(experiment).append("/").append(dateFormatted)
                    .append("/").append(name()).append(".").append(EXTENSION);
            return sb.toString();
        }
    }


}
