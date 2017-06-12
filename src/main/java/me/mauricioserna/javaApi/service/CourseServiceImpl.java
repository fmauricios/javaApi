package me.mauricioserna.javaApi.service;

import me.mauricioserna.javaApi.dao.CourseDao;
import me.mauricioserna.javaApi.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by mauricio on 12/06/17.
 */

@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao _courseDao;

    @Override
    public void saveCourse(Course course) {

    }

    @Override
    public void deleteCourse(Long idCourse) {

    }

    @Override
    public void updateCourse(Course course) {

    }

    @Override
    public List findAllCourses() {
        return null;
    }

    @Override
    public Course findCourseById(Long idCourse) {
        return null;
    }

    @Override
    public Course findByName(String name) {
        return null;
    }

    @Override
    public List<Course> findByIdTeacher(Long idTeacher) {
        return null;
    }
}
