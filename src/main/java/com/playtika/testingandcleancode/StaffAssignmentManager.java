package com.playtika.testingandcleancode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.playtika.testingandcleancode.datasource.BedRepository;
import com.playtika.testingandcleancode.datasource.StaffRepository;
import com.playtika.testingandcleancode.domain.Bed;
import com.playtika.testingandcleancode.domain.Staff;

@Repository
public class StaffAssignmentManager {

    private final ArrayList<Staff> shiftStaff;
    private final ArrayList<Bed> beds;
    private final HashMap<Bed, List<Staff>> bedStaffAssignments;

    public StaffAssignmentManager() {
        StaffRepository staffRepo = new StaffRepository();
        shiftStaff = new ArrayList<>();
        shiftStaff.addAll(staffRepo.getShiftStaff());
        beds = new ArrayList<>();
        BedRepository bedRepo = new BedRepository();
        beds.addAll(bedRepo.getAllBeds());
        bedStaffAssignments = new HashMap<>();
    }

    public List<Staff> getAvailableStaff() {
        ArrayList<Staff> availableStaff = new ArrayList<>();
        for (Staff staff : shiftStaff) {
            boolean staffAssigned = false;
            for (Map.Entry<Bed, List<Staff>> bedListEntry : bedStaffAssignments.entrySet()) {
                if (bedListEntry.getValue()
                                .contains(staff)) {
                    staffAssigned = true;
                }
            }
            if (!staffAssigned) {
                availableStaff.add(staff);
            }
        }
        return Collections.unmodifiableList(availableStaff);
    }

    public List<Bed> getAvailableBeds() {
        ArrayList<Bed> availableBeds = new ArrayList<>();
        for (Bed bed : beds) {
            if (bed.getPatientAssigned() == null) {
                availableBeds.add(bed);
            }
        }
        return Collections.unmodifiableList(availableBeds);
    }
}
