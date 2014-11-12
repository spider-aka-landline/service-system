package servicesystem;

import entities.DipoleData;
import entities.providers.ServiceProvider;
import entities.users.User;
import java.util.Collection;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class BruteForcerData {

    private SortedSet<DipoleData> numbers;
    private final SortedMap<Integer, Collection<User>> usersBase;
    private final SortedMap<Integer, Collection<ServiceProvider>> providersBase;

    public BruteForcerData() {
        numbers = new TreeSet<>();
        usersBase = new TreeMap<>();
        providersBase = new TreeMap<>();
    }

    public Collection<User> getUsers(Integer usersNumber) {
        if (!usersBase.containsKey(usersNumber)) {
            throw new IllegalArgumentException();
        }
        return usersBase.get(usersNumber);
    }

    public Collection<ServiceProvider> getProviders(Integer providersNumber) {
        if (!providersBase.containsKey(providersNumber)) {
            System.err.println(providersBase);
            throw new IllegalArgumentException();
        }
        return providersBase.get(providersNumber);
    }

    public void addNumbers(DipoleData dipoleData) {
        numbers.add(dipoleData);
    }

    public void initProvidersBase(Integer p,
            Collection<ServiceProvider> generateProviders) {
        providersBase.put(p, generateProviders);
    }

    public void putUsers(Integer u, Collection<User> generateUsers) {
        usersBase.put(u, generateUsers);
    }

    public SortedSet<DipoleData> getNumbers() {
        return numbers;
    }
    
    

}
