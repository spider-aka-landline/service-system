package entities.users;

import entities.Params;
import messages.UserResponse;
import messages.ProviderResponse;
import servicesystem.ExperimentsRunner;
import util.IO;

public class User {

    private final long id;

    private final Estimator estimator = new Estimator();
    private final Params requirements;

    /**
     * @param i - User ID
     * @param required - requirements for service
     */
    public User(long i, Params required) {
        id = i;
        requirements = required;
    }

    public UserResponse generateResponse(ProviderResponse pr) {
        Double rvalue = estimator.evaluateEstimate(requirements);
        Double value = estimator.evaluateEstimate(pr.getServiceParameters());
        Boolean diff = estimator.isPositiveDifference(value, rvalue);
        ExperimentsRunner.logExperimentData(value);
        return new UserResponse(value, diff);
    }

    public long getID() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(String.valueOf(id));
        s.append(" ").append(requirements);
        return s.toString();
    }
}
