package messages;

public class UserResponse {
    private final Double estimate;
    private final Boolean positive_difference; // delta = v^u - V^u;

    public UserResponse(Double countEstimate, Boolean diff) {
        estimate = countEstimate;
        positive_difference = diff;
    }
    
    public Double getEstimate(){
        return estimate;
    }
  
    public Boolean isDifferencePositive(){
        return positive_difference;
    }
    
}
