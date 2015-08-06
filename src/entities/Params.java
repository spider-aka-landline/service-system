package entities;

import java.util.Objects;

public class Params {
    public static final int PARAMETERS_QUANTITY = 1;

    private final Double serviceQuality;

    public Params(double[] array){
        this(array[0]);
    }

    public Params(Double sq) {
        serviceQuality = sq;
    }

    public Double getServiceQuality() {
        return serviceQuality;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.serviceQuality);
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
        final Params other = (Params) obj;
        return Objects.equals(this.serviceQuality, other.serviceQuality);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(serviceQuality.toString());
        //s.append(",").append();
        return s.toString();
    }

}
