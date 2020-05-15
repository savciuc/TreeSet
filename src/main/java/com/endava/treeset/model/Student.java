package com.endava.treeset.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.YEARS;

@Builder
@Getter
@Setter
public class Student implements Comparable<Student>
{
    private String name;
    private LocalDate dateOfBirth;
    private String details;

    public Student(String name, LocalDate dateOfBirth, String details) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.details = details;
    }

    public int age() {
        LocalDate now = LocalDate.now();
        LocalDate birthDate = this.dateOfBirth;
        int age = (int) YEARS.between(birthDate, now);
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            var object = (Student)obj;
            boolean isNameEquals = this.name.equalsIgnoreCase(object.name);
            boolean isDateOfBirthEquals = this.dateOfBirth.equals(object.dateOfBirth);
            return isNameEquals & isDateOfBirthEquals;
        }
        return false;
    }

    @Override
    public int compareTo(Student o) {
        int compareName = this.name.compareTo(o.name);
        if(compareName > 0) return 1;
        if(compareName < 0) return -1;

        int compareDateOfBirth = this.dateOfBirth.compareTo(o.dateOfBirth);
        if(compareDateOfBirth > 0) return 1;
        if(compareDateOfBirth < 0) return -1;
        return 0;
    }
}
