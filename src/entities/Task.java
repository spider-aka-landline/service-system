package entities;

import entities.users.User;

public class Task implements Comparable<Task> {

    User sender;
    Integer creationTime;
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

    @Override
    public int compareTo(Task t2) {
        return this.creationTime.compareTo(t2.creationTime);
    }

}
