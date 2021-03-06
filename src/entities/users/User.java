package entities.users;

import entities.ID;
import entities.Params;
import messages.UserResponse;
import messages.ProviderResponse;
import servicesystem.ExperimentsRunner;

public class User {

    private final ID id;

    private final Estimator estimator = new Estimator();
    private final Params requirements;

    /**
     * @param i - User ID
     * @param required - requirements for service
     */
    public User(ID i, Params required) {
        id = i;
        requirements = required;
    }

    public UserResponse generateResponse(ProviderResponse pr) {
        Double rvalue = estimator.evaluateEstimate(requirements);
        Double value = estimator.evaluateEstimate(pr.getServiceParameters());
        Boolean diff = estimator.isPositiveDifference(value, rvalue);
        ExperimentsRunner.logExperimentData(pr, value);
        return new UserResponse(value, diff);
    }

    public ID getID() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        long tempId = this.id.getValue();
        hash = 23 * hash + (int) (tempId ^ (tempId >>> 32));
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
        s.append(",").append(requirements);
        return s.toString();
    }
}
