package entities.providers;

import entities.ID;
import entities.Params;
import entities.Task;
import messages.ProviderResponse;

public class ServiceProvider implements Comparable<ServiceProvider> {

    protected final ID id;
    Params properties;

    /**
     *
     * @param i - provider ID
     * @param pr - service parameters
     */
    public ServiceProvider(ID i, Params pr) {
        id = i;
        properties = pr;
    }

    public ProviderResponse processUserTask(Task t) {
        return new ProviderResponse(id, t, properties);
    }

    public ID getID() {
        return id;
    }

    public Params getProperties() {
        return properties;
    }

    public void setProperties(Params p) {
        properties = p;
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        long tempId = this.id.getValue();
        hash = 23 * hash + (int) (tempId ^ ( tempId >>> 32));
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


    @Override
    public int compareTo(ServiceProvider o) {
        long tempId = this.id.getValue();
        return this.id.compareTo(o.getID());
    }
}
