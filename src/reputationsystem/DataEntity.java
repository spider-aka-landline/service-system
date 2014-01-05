package reputationsystem;

public class DataEntity {

    private Double reputation;
    private Double expectation; 
    
    DataEntity(){
        this(0.0,0.0);
    }
     
    DataEntity(Double rep, Double expect){
        reputation = rep;
        expectation = expect;
    }
    
    public Double getReputation(){
        return reputation;
    }
    
    public Double getExpectation(){
        return expectation;
    }
    
    public void setReputation (Double estimate){
        reputation = estimate;
    }
    
    public void setExpectation (Double expect){
        expectation = expect;
    }    

}

