package me.mauricioserna.javaApi.dao;

import javax.management.Query;
import java.util.List;
import me.mauricioserna.javaApi.model.Teacher;

/**
 * Created by mauricio on 11/06/17.
 */

public class TeacherDaoImpl extends AbstractSession implements TeacherDao {

    public void saveTeacher(Teacher teacher) {
        getSession().persist(teacher);
    }

    public void deleteTeacher(Long idTeacher) {
        Teacher teacher = findTeacherById(idTeacher);

        if (teacher != null) {
        	getSession().delete(teacher);
        }
    }

    public void updateTeacher(Teacher teacher) {
        getSession().update(teacher);
    }

	public List<Teacher> findAllTeachers() {
        return getSession().createQuery("from Teacher").list();
    }

    public Teacher findTeacherById(Long idTeacher) {
        return getSession().get(Teacher.class, idTeacher);
    }

    public Teacher findByName(String name) {
        return (Teacher) getSession().createQuery("from Teacher where name = :name")
                           .setParameter("name", name).uniqueResult();
    }

}
