package me.mauricioserna.javaApi.dao;

import me.mauricioserna.javaApi.model.Course;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by mauricio on 12/06/17.
 */

@Repository
@Transactional
public class CourseDaoImpl extends AbstractSession implements CourseDao {

    @Override
    public void saveCourse(Course course) {
        getSession().persist(course);
    }

    @Override
    public void deleteCourse(Long idCourse) {
        Course course = findCourseById(idCourse);

        if (course != null) {
            getSession().delete(course);
        }
    }

    @Override
    public void updateCourse(Course course) {
        getSession().update(course);
    }

    @Override
    public List<Course> findAllCourses() {
        return getSession().createQuery("from Course").list();
    }

    @Override
    public Course findCourseById(Long idCourse) {
        return getSession().get(Course.class, idCourse);
    }

    @Override
    public Course findByName(String name) {
        return (Course) getSession().createQuery("from Course where name = :name")
                .setParameter("name", name).uniqueResult();
    }

    @Override
    public List<Course> findByIdTeacher(Long idTeacher) {
        return (List<Course>) getSession()
                .createQuery("from Course c join c.teacher t where t.idTeacher = :idTeacher")
                .setParameter("idTeacher", idTeacher).list();
    }
}
