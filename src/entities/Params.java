package entities;

public class Params {

    private final Double serviceQuality;

    public Params(Double sq) {
        serviceQuality = sq;
    }

    public Double getServiceQuality() {
        return serviceQuality;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(serviceQuality.toString());
        //s.append(" ").append();
        return s.toString();
    }

}
