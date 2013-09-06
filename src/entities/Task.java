package entities;

public class Task implements Comparable<Task>{

    User sender;
    Long creationTime;
    //some inner data
    //some result
    Integer complitionTime;
    

    public Task(){
        creationTime = new Long(System.currentTimeMillis());
    }
    
    public Task(User who){
        this();
        sender = who;
    }
    
    public User getUser() {
        return sender;
    }

    public Integer getComplitionTime() {
        return complitionTime;
    }

    @Override
    public int compareTo(Task t2) {
       return this.creationTime.compareTo(t2.creationTime);
    }
}
