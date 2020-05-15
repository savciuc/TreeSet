package com.endava.treeset;

import com.endava.treeset.model.Student;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class MainApp {

    public static void main(String[] args) {
        TreeSet<Student> treeSet = new TreeSet<Student>();
        Student firstStudent = Student.builder().name("Lily").details("Details").dateOfBirth(LocalDate.of(2007,8,10)).build();
        Student secondStudent = Student.builder().name("Ann").details("Ann Details").dateOfBirth(LocalDate.of(2009,10,12)).build();
        Student thirdStudent = Student.builder().name("Mary").details("Mary Details").dateOfBirth(LocalDate.of(2000,10,10)).build();
        Student[] students = new Student[] {firstStudent,secondStudent,thirdStudent};

        treeSet.addAll(Arrays.asList(students));
        treeSet.remove(treeSet.first());
        if (treeSet.contains(firstStudent)) {
            System.out.println("TreeSet contains the element: " + firstStudent);
        } else {
            System.out.println("TreeSet doesn't contain the element: " + firstStudent);
        }

        for (Student student : treeSet) {
            System.out.println(student.getName() + " is " + student.age() + " years old");
        }
        
        System.out.println(treeSet);
        treeSet.removeAll(Arrays.asList(students));
        System.out.println("TreeSet is empty: " + treeSet);
    }
}
