package com.playtika.testingandcleancode.domain;

public record Staff(int staffId, String name, StaffRole role) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}

        Staff staff = (Staff) o;

        return staffId == staff.staffId;
    }

    @Override
    public int hashCode() {
        return staffId;
    }
}
