package reputationsystem;

class DataEntity {

    private Double reputation;
    private Double expect_value; 
    
    DataEntity(){
        this(0.0,0.0);
    }
     
    DataEntity(Double rep, Double expect){
        reputation = rep;
        expect_value = expect;
    }
    
    public Double getReputation(){
        return reputation;
    }
    
    public Double getExpectation(){
        return expect_value;
    }
    
    public void setReputation (Double estimate){
        reputation = estimate;
    }
    
    public void setExpectation (Double expect){
        expect_value = expect;
    }    

}

