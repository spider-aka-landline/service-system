package entities;

public class Task implements Entity, Comparable<Task>{

    User sender;
    Integer creationTime;
    //some inner data
    //some result
    //Integer complitionTime;
    
    public Task(User who, Integer time){
        creationTime = time;
        sender = who;
    }
    
    public User getUser() {
        return sender;
    }

    /*
    public Integer getComplitionTime() {
        return complitionTime;
    }
    */

    @Override
    public int compareTo(Task t2) {
       return this.creationTime.compareTo(t2.creationTime);
    }

    @Override
    public void printProperty() {
        System.out.print(creationTime+" ");
    }
}
