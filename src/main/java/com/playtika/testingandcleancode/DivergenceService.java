package com.playtika.testingandcleancode;

import java.util.List;

import org.springframework.stereotype.Service;

import com.playtika.testingandcleancode.domain.Bed;
import com.playtika.testingandcleancode.domain.Patient;
import com.playtika.testingandcleancode.domain.Priority;
import com.playtika.testingandcleancode.domain.Staff;
import com.playtika.testingandcleancode.domain.StaffRole;

@Service
public class DivergenceService {

    private final StaffAssignmentManager manager;
    private final EmergencyResponseService transportService;

    private boolean redDivergence;
    private boolean yellowDivergence;
    private boolean greenDivergence;
    private int redCount;
    private int yellowCount;
    private int greenCount;
    private final int allowedCount;
    private final int redOver;
    private final int yellowOver;
    private final int greenOver;

    public DivergenceService(StaffAssignmentManager manager, EmergencyResponseService transportService) {
        this.manager = manager;
        this.transportService = transportService;
        this.redDivergence = false;
        this.yellowDivergence = false;
        this.greenDivergence = false;
        this.redCount = 0;
        this.yellowCount = 0;
        this.greenCount = 0;
        this.allowedCount = 2;
        this.redOver = 0;
        this.yellowOver = 1;
        this.greenOver = 4;
    }

    public void check() {
        int[] red = {1, 2};
        int[] yellow = {1, 1};
        int[] green = {0, 1};
        boolean redIncremented = false;
        boolean yellowIncremented = false;
        boolean greenIncremented = false;
        List<Patient> patients = transportService.fetchInboundPatients();
        List<Staff> staff = manager.getAvailableStaff();
        List<Bed> beds = manager.getAvailableBeds();
        int bedcrits = 0;
        int redin = 0;
        int yellowin = 0;
        int greenin = 0;
        int[] staffcur = {0, 0};
        int[] need = {0, 0};

        for (Bed bed : beds) {
            if (bed.isCriticalCare()) {
                bedcrits++;
            }
        }
        for (Patient patient : patients) {
            if (Priority.RED.equals(patient.priority())) {
                redin++;
            } else if (Priority.YELLOW.equals(patient.priority())) {
                yellowin++;
            } else if (Priority.GREEN.equals(patient.priority())) {
                greenin++;
            }
        }
        for (Staff cur : staff) {
            if (StaffRole.DOCTOR.equals(cur.role())) {
                staffcur[0]++;
            } else if (StaffRole.NURSE.equals(cur.role())) {
                staffcur[1]++;
            }
        }
        if (redin > (bedcrits + redOver)) {
            redCount++;
            redIncremented = true;
        }
        if (yellowin + greenin > (beds.size() - bedcrits + yellowOver + greenOver)) {
            if ((greenin > (beds.size() - bedcrits + greenOver)) && (yellowin <= (beds.size() - bedcrits + yellowOver))) {
                greenCount++;
                greenIncremented = true;
            } else {
                greenCount++;
                yellowCount++;
                greenIncremented = true;
                yellowIncremented = true;
            }
        }
        need[0] = redin * red[0];
        need[0] += yellowin * yellow[0];
        need[0] += greenin * green[0];
        need[1] = redin * red[1];
        need[1] += yellowin * yellow[1];
        need[1] += greenin * green[1];
        if (need[0] > staffcur[0]) {
            int diff = need[0] - staffcur[0];
            if ((greenin * green[0]) >= diff) {
                if (!greenIncremented) {
                    greenIncremented = true;
                    greenCount++;
                }
            } else {
                int both = (yellowin * yellow[0]) + (greenin * green[0]);
                if (both >= diff) {
                    if (!greenIncremented) {
                        greenIncremented = true;
                        greenCount++;
                    }
                    if (!yellowIncremented) {
                        yellowIncremented = true;
                        yellowCount++;
                    }
                } else {
                    if (!greenIncremented) {
                        greenIncremented = true;
                        greenCount++;
                    }
                    if (!yellowIncremented) {
                        yellowIncremented = true;
                        yellowCount++;
                    }
                    if (!redIncremented) {
                        redIncremented = true;
                        redCount++;
                    }
                }
            }
        }
        if (need[1] > staffcur[1]) {
            int diff = need[1] - staffcur[1];
            if ((greenin * green[1]) >= diff) {
                if (!greenIncremented) {
                    greenIncremented = true;
                    greenCount++;
                }
            } else {
                int both = (yellowin * yellow[1]) + (greenin * green[1]);
                if (both >= diff) {
                    if (!greenIncremented) {
                        greenIncremented = true;
                        greenCount++;
                    }
                    if (!yellowIncremented) {
                        yellowIncremented = true;
                        yellowCount++;
                    }
                } else {
                    if (!greenIncremented) {
                        greenIncremented = true;
                        greenCount++;
                    }
                    if (!yellowIncremented) {
                        yellowIncremented = true;
                        yellowCount++;
                    }
                    if (!redIncremented) {
                        redIncremented = true;
                        redCount++;
                    }
                }
            }
        }
        if (redIncremented) {
            if ((redCount > allowedCount) && !redDivergence) {
                redDivergence = true;
                transportService.requestInboundDiversion(Priority.RED);
                System.out.println("Entered divergence for RED priority patients!" + true);
                redCount = 0;
            }
        } else {
            redCount = 0;
            if (redDivergence) {
                transportService.removeInboundDiversion(Priority.RED);
                System.out.println("Ended divergence for RED priority patients." + false);
                redDivergence = false;
            }
        }
        if (yellowIncremented) {
            if ((yellowCount > allowedCount) && !yellowDivergence) {
                yellowDivergence = true;
                transportService.requestInboundDiversion(Priority.YELLOW);
                System.out.println("Entered divergence for YELLOW priority patients!" + true);
                yellowCount = 0;
            }
        } else {
            yellowCount = 0;
            if (yellowDivergence) {
                transportService.removeInboundDiversion(Priority.YELLOW);
                System.out.println("Ended divergence for YELLOW priority patients." + false);
                yellowDivergence = false;
            }
        }
        if (greenIncremented) {
            if ((greenCount > allowedCount) && !greenDivergence) {
                greenDivergence = true;
                transportService.requestInboundDiversion(Priority.GREEN);
                System.out.println("Entered divergence for GREEN priority patients! " + true);
                greenCount = 0;
            }
        } else {
            greenCount = 0;
            if (greenDivergence) {
                transportService.removeInboundDiversion(Priority.GREEN);
                System.out.println("Ended divergence for GREEN priority patients." + false);
                greenDivergence = false;
            }
        }
    }
}
