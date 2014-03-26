package entities;

import java.util.Objects;

public class DipoleData implements Comparable<DipoleData> {

    private final Integer usersNumber;
    private final Integer providersNumber;

    public DipoleData(Integer i, Integer j) {
        usersNumber = i;
        providersNumber = j;
    }

    public Integer getUserNumber() {
        return usersNumber;
    }

    public Integer getProviderNumber() {
        return providersNumber;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.providersNumber);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DipoleData other = (DipoleData) obj;
        if (!Objects.equals(this.usersNumber, other.usersNumber)) {
            return false;
        }
        return Objects.equals(this.providersNumber, other.providersNumber);
    }

    @Override
    public int compareTo(DipoleData o) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        //this optimization is usually worthwhile, and can
        //always be added
        if (this == o) {
            return EQUAL;
        }

        //objects, including type-safe enums, follow this form
        //note that null objects will throw an exception here
        int comparison = this.usersNumber.compareTo(o.usersNumber);
        if (comparison != EQUAL) {
            return comparison;
        }

        comparison = this.providersNumber.compareTo(o.providersNumber);
        if (comparison != EQUAL) {
            return comparison;
        }

        if (this.usersNumber > o.usersNumber) {
            return AFTER;
        } else if (this.usersNumber < o.usersNumber) {
            return BEFORE;
        } else if (this.providersNumber > o.providersNumber) {
            return AFTER;
        }

        //all comparisons have yielded equality
        //verify that compareTo is consistent with equals (optional)
        assert this.equals(o) : "compareTo inconsistent with equals.";

        return EQUAL;
    }

}
