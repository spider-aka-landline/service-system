package messages;

public class UserResponse {

    private final Double estimate;
    private final Boolean positiveDifference; // delta = v^u - V^u;

    public UserResponse(Double est, Boolean diff) {
        estimate = est;
        positiveDifference = diff;
    }

    public Double getEstimate() {
        return estimate;
    }

    public Boolean getDifferenceSign() {
        return positiveDifference;
    }

}
