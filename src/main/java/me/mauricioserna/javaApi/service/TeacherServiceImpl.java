package me.mauricioserna.javaApi.service;

import me.mauricioserna.javaApi.dao.TeacherDao;
import me.mauricioserna.javaApi.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by mauricio on 12/06/17.
 */

@Service("teacherService")
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao _teacherDao;

    @Override
    public void saveTeacher(Teacher teacher) {
        _teacherDao.saveTeacher(teacher);
    }

    @Override
    public void deleteTeacher(Long idTeacher) {
        _teacherDao.deleteTeacher(idTeacher);
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        _teacherDao.updateTeacher(teacher);
    }

    @Override
    public List findAllTeachers() {
        return _teacherDao.findAllTeachers();
    }

    @Override
    public Teacher findTeacherById(Long idTeacher) {
        return _teacherDao.findTeacherById(idTeacher);
    }

    @Override
    public Teacher findByName(String name) {
        return _teacherDao.findByName(name);
    }
}
