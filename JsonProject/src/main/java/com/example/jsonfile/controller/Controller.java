package com.example.jsonfile.controller;

import com.example.jsonfile.dto.Student;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    private final ObjectMapper mapper;

    @Autowired
    public Controller(ObjectMapper mapper) {
        this.mapper = mapper;
    }

/////////////////////////////////////post////////////////////////////////////////////////////////////
    @PostMapping("/postdata")
    public ResponseEntity<String> postdata(@RequestBody Student jsonPojo) {
        try {
            File filepath = new File("D:\\JBPMWORKSPACE\\JBPM DIAGRAM\\JsonProject\\src\\main\\resources\\data.json");
            TypeReference<List<Student>> reference = new TypeReference<List<Student>>() {};
    
            List<Student> jsonList = new ArrayList<>();
    
            if (filepath.exists()) {
                FileInputStream fileInputStream = new FileInputStream(filepath);
                jsonList = mapper.readValue(fileInputStream, reference);
            }
    
            jsonList.add(jsonPojo);
    
            try (FileWriter fileWriter = new FileWriter(filepath)) {
                if (jsonList.isEmpty()) {
                    fileWriter.write("[]");
                } else {
                    mapper.writeValue(fileWriter, jsonList);
                }
            }
            return ResponseEntity.ok("Data posted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error posting data");
        }
    }
    
////////////////////////////////////get all////////////////////////////////////////////////////////////
    @GetMapping("/getall")
    public ResponseEntity<List<Student> > getAllStudents() throws IOException {
        File filepath = new File("D:\\JBPMWORKSPACE\\JBPM DIAGRAM\\JsonProject\\src\\main\\resources\\data.json");
        TypeReference<List<Student>> reference = new TypeReference<List<Student>>() {};

        List<Student> jsonList = new ArrayList<>();

        if (filepath.exists()) {
            FileInputStream fileInputStream = new FileInputStream(filepath);
            jsonList = mapper.readValue(fileInputStream, reference);
        }

        return new ResponseEntity<>(jsonList, HttpStatus.OK);
    }

/////////////////////////////////////get by id////////////////////////////////////////////////////////////
    
    @GetMapping("/getbyid/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) throws IOException {
        File filepath = new File("D:\\JBPMWORKSPACE\\JBPM DIAGRAM\\JsonProject\\src\\main\\resources\\data.json");
        TypeReference<List<Student>> reference = new TypeReference<List<Student>>() {};

        List<Student> jsonList = new ArrayList<>();

        if (filepath.exists()) {
            FileInputStream fileInputStream = new FileInputStream(filepath);
            jsonList = mapper.readValue(fileInputStream, reference);
        }

        System.out.println("JSON List: " + jsonList); // Add this line for logging

        Student student = jsonList.stream()
                .filter(s -> studentId.equals(s.getId()))
                .findFirst()
                .orElse(null);

        if (student != null) {
            System.out.println("Found Student: " + student); // Add this line for logging
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            System.out.println("Student not found."); // Add this line for logging
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


/////////////////////////////////////update by id////////////////////////////////////////////////////////////

    @PutMapping("/update/{studentId}")
    public ResponseEntity<String> updateStudentById(@PathVariable Long studentId, @RequestBody Student updatedStudent) {
        File filepath = new File("D:\\JBPMWORKSPACE\\JBPM DIAGRAM\\JsonProject\\src\\main\\resources\\data.json");
        TypeReference<List<Student>> reference = new TypeReference<List<Student>>() {};

        List<Student> jsonList = new ArrayList<>();

        try {
            if (filepath.exists()) {
                FileInputStream fileInputStream = new FileInputStream(filepath);
                jsonList = mapper.readValue(fileInputStream, reference);
            }

            Student existingStudent = jsonList.stream()
                    .filter(s -> studentId.equals(s.getId()))
                    .findFirst()
                    .orElse(null);

            if (existingStudent != null) {
                existingStudent.setName(updatedStudent.getName());
                existingStudent.setAge(updatedStudent.getAge());
                
                try (FileWriter fileWriter = new FileWriter(filepath)) {
                    mapper.writeValue(fileWriter, jsonList);
                }
                return ResponseEntity.ok("Student updated successfully");
            } else {
                return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating student");
        }
    }
////////////////////////////////////delete by id/////////////////////////////////////////////////////
    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long studentId) {
        File filepath = new File("D:\\JBPMWORKSPACE\\JBPM DIAGRAM\\JsonProject\\src\\main\\resources\\data.json");
        TypeReference<List<Student>> reference = new TypeReference<List<Student>>() {};

        List<Student> jsonList = new ArrayList<>();

        try {
            if (filepath.exists()) {
                FileInputStream fileInputStream = new FileInputStream(filepath);
                jsonList = mapper.readValue(fileInputStream, reference);
            }

            boolean removed = jsonList.removeIf(s -> studentId.equals(s.getId()));

            if (removed) {
                try (FileWriter fileWriter = new FileWriter(filepath)) {
                    mapper.writeValue(fileWriter, jsonList);
                }
                return ResponseEntity.ok("Student deleted successfully");
            } else {
                return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting student");
        }
    }
/////////////////////////////////////compare by id////////////////////////////////////////////////////////////
    @PostMapping("/compare-common")
    public ResponseEntity<List<Student>> compareCommonDataWithExternalFile() {
        try {
            // Load the data from the application's JSON file
            File applicationDataFile = new File("D:\\JBPMWORKSPACE\\JBPM DIAGRAM\\JsonProject\\src\\main\\resources\\data.json");
            TypeReference<List<Student>> reference = new TypeReference<List<Student>>() {};

            List<Student> existingStudentData = new ArrayList<>();

            if (applicationDataFile.exists()) {
                FileInputStream fileInputStream = new FileInputStream(applicationDataFile);
                existingStudentData = mapper.readValue(fileInputStream, reference);
            }

            // Load the second JSON file from a separate location
            File externalDataFile = new File("D:\\JBPMWORKSPACE\\JBPM DIAGRAM\\JsonProject\\src\\main\\resources\\data2.json");

            if (!externalDataFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
            }

            FileInputStream externalFileInputStream = new FileInputStream(externalDataFile);
            List<Student> externalStudentData = mapper.readValue(externalFileInputStream, reference);

            // Find common data between the two sets of data based on student IDs
            List<Student> commonData = new ArrayList<>();

            for (Student existingStudent : existingStudentData) {
                for (Student externalStudent : externalStudentData) {
                    if (existingStudent.getId() == externalStudent.getId()) {
                        commonData.add(existingStudent);
                    }
                }
            }

            // Print common data
            System.out.println("Common Data:");
            for (Student student : commonData) {
                System.out.println(student);
            }

            return ResponseEntity.ok(commonData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }
////////////////////////////////compare all///////////////////////////////////////////////////////////////
    @PostMapping("/compare-all")
    public ResponseEntity<List<Student>> compareAllDataWithExternalFile() {
        try {
            // Load the data from the application's JSON file
            File applicationDataFile = new File("D:\\JBPMWORKSPACE\\JBPM DIAGRAM\\JsonProject\\src\\main\\resources\\data.json");
            TypeReference<List<Student>> reference = new TypeReference<List<Student>>() {};

            List<Student> existingStudentData = new ArrayList<>();

            if (applicationDataFile.exists()) {
                FileInputStream fileInputStream = new FileInputStream(applicationDataFile);
                existingStudentData = mapper.readValue(fileInputStream, reference);
            }

            // Load the second JSON file from a separate location
            File externalDataFile = new File("D:\\JBPMWORKSPACE\\JBPM DIAGRAM\\JsonProject\\src\\main\\resources\\data2.json");

            if (!externalDataFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
            }

            FileInputStream externalFileInputStream = new FileInputStream(externalDataFile);
            List<Student> externalStudentData = mapper.readValue(externalFileInputStream, reference);

            // Find common data between the two sets of data
            List<Student> commonData = new ArrayList<>();

            for (Student existingStudent : existingStudentData) {
                for (Student externalStudent : externalStudentData) {
                    if (existingStudent.getId() == externalStudent.getId()
                        && existingStudent.getName().equals(externalStudent.getName())
                        && existingStudent.getAge().equals(externalStudent.getAge())) {
                        commonData.add(existingStudent);
                    }
                }
            }

            // Print common data
            System.out.println("Common Data:");
            for (Student student : commonData) {
                System.out.println(student);
            }

            return ResponseEntity.ok(commonData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }


    }
