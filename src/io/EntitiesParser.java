package io;

import Jama.Matrix;
import entities.ID;
import entities.Params;
import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;

import java.util.Collection;

/**
 * Created by Spider on 04.02.2015.
 */
public class EntitiesParser {

    public static User createUser(Matrix input){
        User user;
        ID userId = getID(input);
        Params params = createParams(input);
        user = new User(userId,params);
        return user;
    }

    public static ServiceProvider createServiceProvider(Matrix input){
        ServiceProvider serviceProvider;
        ID providerId = getID(input);
        Params params = createParams(input);
        serviceProvider = new ServiceProvider(providerId,params);
        return serviceProvider;
    }

    private static Params createParams(Matrix input){
        Params params;
        Matrix paramsData = getData(input,1,input.getColumnDimension()-1);
        double[] array = paramsData.getArray()[0];
        params = new Params(array);
        return params;
    }

    private static ID getID(Matrix input){
        return new ID(getData(input,0));
    }

    private static double getData(Matrix input, int column){
        return input.get(0,column);
    }
    private static Matrix getData(Matrix input, int column1, int column2){
        return input.getMatrix(0, 0, column1, column2);
    }


    /*
    userID, arrivalTime
     */
    public static Task createTask(Matrix input, Collection<User> usersCollection){
        Task task;
        ID userId = new ID(getData(input,0));
        double arrivalTime = getData(input,1);
        User user = getUserById(userId,usersCollection);
        task = new Task(user, (int) arrivalTime);
        return task;
    }

    private static User getUserById(ID id, Collection<User> usersCollection){
        for (User temp: usersCollection){
            if (temp.getID().equals(id)) return temp;
        }
        return null;
    }


}
