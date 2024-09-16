package com.playtika.testingandcleancode.datasource;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.playtika.testingandcleancode.domain.Staff;
import com.playtika.testingandcleancode.domain.StaffRole;

public class StaffRepository {

    private String staffFile = "/staff.csv";
    private Scanner scanner;

    public StaffRepository() {
        try {
            URL fileUrl = getClass().getResource(staffFile);
            this.scanner = new Scanner(new File(fileUrl.getFile()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Staff> getShiftStaff() {
        ArrayList<Staff> staffList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(",");
            int empId = Integer.parseInt(values[0]);
            String name = values[1];
            String roleString = values[2];
            Staff staff = new Staff(empId, name, StaffRole.valueOf(roleString));
            staffList.add(staff);
        }
        return staffList;
    }
}
