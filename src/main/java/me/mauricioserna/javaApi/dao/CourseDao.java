package me.mauricioserna.javaApi.dao;

import me.mauricioserna.javaApi.model.Course;
import java.util.List;
/**
 * Created by mauricio on 12/06/17.
 */
public interface CourseDao {
    void saveCourse(Course course);
    void deleteCourse(Long idCourse);
    void updateCourse(Course course);
    List findAllCourses();
    Course findCourseById(Long idCourse);
    Course findByName(String name);
    List<Course> findByIdTeacher(Long idTeacher);
}
