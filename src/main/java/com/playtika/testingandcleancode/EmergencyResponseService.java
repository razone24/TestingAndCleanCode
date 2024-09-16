package com.playtika.testingandcleancode;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import com.playtika.testingandcleancode.domain.Patient;
import com.playtika.testingandcleancode.domain.Priority;

@Component
public class EmergencyResponseService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public List<Patient> fetchInboundPatients() {
        try {
            return OBJECT_MAPPER.readValue(EmergencyResponseService.class.getClassLoader()
                                                                         .getResourceAsStream("patients.json"), new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void requestInboundDiversion(Priority priority) {
        System.out.println("Requesting inbound diversion for priority " + priority);
    }

    public void removeInboundDiversion(Priority priority) {
        System.out.println("Removing inbound diversion for priority " + priority);
    }
}
