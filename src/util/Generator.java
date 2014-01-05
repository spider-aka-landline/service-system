package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import entities.Params;
import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;
import math.Poisson;
import math.StdRandom;

public class Generator {
    
    public Collection<User> generateUsers(Integer num) {
        Collection<User> tempU = new HashSet();
        for (int i = 0; i < num; i++) {
            tempU.add(new User(new Params(StdRandom.uniform(0, 1))));
        }
        return tempU;
    }

    public Collection<ServiceProvider> generateProviders(Integer num) {
        Collection<ServiceProvider> tempP = new HashSet();
        for (int i = 0; i < num; i++) {
            tempP.add(new ServiceProvider(new Params(StdRandom.uniform(0, 1))));
        }
        return tempP;
    }

    public Collection<Task> generateTasks(Collection<User> users, Integer num) {
        Collection<Task> tempT = new ArrayList();
        Poisson generator = new Poisson(42.0);
        for (int i = 0; i < num; i++) {
            tempT.add(new Task(users.iterator().next(), generator.next()));
        }
        return tempT;
    }

}
