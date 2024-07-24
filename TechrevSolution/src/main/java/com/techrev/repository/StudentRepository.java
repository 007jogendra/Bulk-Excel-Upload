package com.techrev.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.techrev.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByQualification(String qualification);

    List<Student> findByAge(int age);

    @Query("SELECT s FROM Student s WHERE s.collegeName = :college AND s.qualification = :qualification AND s.branch = :branch ORDER BY s.age ASC")
    List<Student> findByCollegeNameAndQualificationAndBranchOrderByAgeAsc(@Param("college") String college, @Param("qualification") String qualification, @Param("branch") String branch);

    @Query("SELECT s FROM Student s WHERE s.collegeName = :college AND s.qualification = :qualification AND s.branch = :branch ORDER BY s.age DESC")
    List<Student> findByCollegeNameAndQualificationAndBranchOrderByAgeDesc(@Param("college") String college, @Param("qualification") String qualification, @Param("branch") String branch);
}
