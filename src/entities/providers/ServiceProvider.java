package entities.providers;

import entities.Params;
import entities.Task;
import messages.ProviderResponse;

public class ServiceProvider {

    private final long id;
    Params properties;

    /**
     *
     * @param i - provider ID
     * @param pr - service parameters
     */
    public ServiceProvider(long i, Params pr) {
        id = i;
        properties = pr;
    }

    public ProviderResponse processUserTask(Task t) {
        return new ProviderResponse(properties);
    }

    public long getID() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final ServiceProvider other = (ServiceProvider) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(String.valueOf(id));
        s.append(" ").append(properties);
        return s.toString();
    }
}
