package entities;

import entities.users.User;

public class Task implements Comparable<Task> {

    private User sender;
    private Integer creationTime;
    //some inner data
    //type
    //Integer complitionTime;

    public Task(User who, Integer time) {
        creationTime = time;
        sender = who;
    }

    public User getSender() {
        return sender;
    }

    public Integer getCreationTime() {
        return creationTime;
    }
    
    @Override
    public int compareTo(Task t2) {
        return this.creationTime.compareTo(t2.creationTime);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(String.valueOf(sender.getID()));
        s.append(",").append(creationTime);
        return s.toString();
    }
}
