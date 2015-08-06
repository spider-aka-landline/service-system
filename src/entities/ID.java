package entities;

/**
 * Created by Spider on 04.02.2015.
 */
public class ID implements Comparable<ID>{
    private final long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ID id1 = (ID) o;

        if (id == id1.id) return true;

        return false;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public ID(long input){
        id = input;
    }
    public ID(double input){
        id = (long) input;
    }

    public long getValue(){
        return id;
    }

    @Override
    public int compareTo(ID o) {
        return (id < o.id) ? -1
        : (id > o.id) ? 1 : 0;
    }

    public String toString(){

        return String.valueOf(id);
    }}
