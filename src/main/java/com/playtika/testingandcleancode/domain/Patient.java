package com.playtika.testingandcleancode.domain;

import java.time.LocalDate;

public record Patient(int transportId, String name, LocalDate birthDate, Priority priority, String condition) {

}
