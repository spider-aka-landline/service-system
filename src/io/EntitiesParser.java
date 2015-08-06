package io;

import Jama.Matrix;
import entities.ID;
import entities.Params;
import entities.Task;
import entities.generators.EntitiesGenerator;
import entities.providers.ServiceProvider;
import entities.users.User;

import java.util.Collection;

/**
 * Created by Spider on 04.02.2015.
 */
public class EntitiesParser {

    public static final EntitiesGenerator generator = EntitiesGenerator.getInstance();

    public static User parseUser(Matrix input){
        User user;
        ID userId = getID(input);
        Params params = createParams(input);
        user = generator.createUser(userId, params);
        return user;
    }

    public static ServiceProvider parseServiceProvider(Matrix input){
        ServiceProvider serviceProvider;
        ID providerId = getID(input);
        Params params = createParams(input);
        serviceProvider = generator.createProvider(providerId, params);
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
        task = generator.createTask(user, (int) arrivalTime);
        return task;
    }

    private static User getUserById(ID id, Collection<User> usersCollection){
        if (usersCollection==null) throw new IllegalArgumentException("UsersCollection expected, but null found");
        if (usersCollection.isEmpty()) throw new IllegalArgumentException("Empty usersCollection");

        for (User temp: usersCollection){
            if (temp.getID().equals(id)) return temp;
        }
        return null;
    }


}
