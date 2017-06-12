package me.mauricioserna.javaApi.dao;

import javax.management.Query;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import me.mauricioserna.javaApi.model.Teacher;
import me.mauricioserna.javaApi.model.TeacherSocialMedia;
import org.springframework.stereotype.Repository;

/**
 * Created by mauricio on 11/06/17.
 */

@Repository
@Transactional
public class TeacherDaoImpl extends AbstractSession implements TeacherDao {

    @Override
    public void saveTeacher(Teacher teacher) {
        getSession().persist(teacher);
    }

    @Override
    public void deleteTeacher(Long idTeacher) {
        Teacher teacher = findTeacherById(idTeacher);

        if (teacher != null) {
            Iterator<TeacherSocialMedia> i = teacher.getTeacherSocialMedias().iterator();

            while (i.hasNext()) {
                TeacherSocialMedia teacherSocialMedia = i.next();
                i.remove();
                getSession().delete(teacherSocialMedia);
            }

            teacher.getTeacherSocialMedias().clear();
            getSession().delete(teacher);
        }
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        getSession().update(teacher);
    }

    @Override
	public List<Teacher> findAllTeachers() {
        return getSession().createQuery("from Teacher").list();
    }

    @Override
    public Teacher findTeacherById(Long idTeacher) {
        return getSession().get(Teacher.class, idTeacher);
    }

    @Override
    public Teacher findByName(String name) {
        return (Teacher) getSession().createQuery("from Teacher where name = :name")
                           .setParameter("name", name).uniqueResult();
    }

}
