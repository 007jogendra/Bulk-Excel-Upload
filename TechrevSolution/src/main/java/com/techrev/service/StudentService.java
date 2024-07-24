package com.techrev.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.techrev.entity.Student;
import com.techrev.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> saveStudentsFromExcel(MultipartFile file) throws IOException {
        List<Student> students = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            students = StreamSupport.stream(sheet.spliterator(), false)
                .skip(1) // Skip header row
                .map(this::rowToStudent)
                .collect(Collectors.toList());
        }
        return studentRepository.saveAll(students);
    }

    private Student rowToStudent(Row row) {
        Student student = new Student();
        student.setName(row.getCell(0).getStringCellValue());
        student.setAge((int) row.getCell(1).getNumericCellValue());
        student.setAddress(row.getCell(2).getStringCellValue());
        student.setCollegeName(row.getCell(3).getStringCellValue());
        student.setQualification(row.getCell(4).getStringCellValue());
        student.setBranch(row.getCell(5).getStringCellValue());
        return student;
    }
       
    public List<Student> getStudentsByQualification(String qualification) 
    {
        List<Student> students = studentRepository.findByQualification(qualification);
        return students;
    }

    public List<Student> getStudentByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> getStudentsReport(String collegeName, String qualification, String branch, String order)  
    {
    	
       
        if (order.trim().equalsIgnoreCase("asc"))
        {
          
            return studentRepository.findByCollegeNameAndQualificationAndBranchOrderByAgeAsc(collegeName, qualification, branch);
        } 
        else 
        {
            
            return studentRepository.findByCollegeNameAndQualificationAndBranchOrderByAgeDesc(collegeName, qualification, branch);
        }
    }
}
