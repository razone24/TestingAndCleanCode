package com.playtika.testingandcleancode.domain;

import lombok.Getter;

@Getter
public class Bed {

    private final int bedId;
    private final boolean criticalCare;
    private Patient patientAssigned;

    public Bed(int bedId, boolean criticalCare) {
        this.bedId = bedId;
        this.criticalCare = criticalCare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}

        Bed bed = (Bed) o;

        return bedId == bed.bedId;
    }

    @Override
    public int hashCode() {
        return bedId;
    }
}
