package me.mauricioserna.javaApi.dao;

import me.mauricioserna.javaApi.model.Teacher;

import java.util.List;

/**
 * Created by mauricio on 11/06/17.
 */
public interface TeacherDao {
    void saveTeacher(Teacher teacher);
    void deleteTeacher(Long idTeacher);
    void updateTeacher(Teacher teacher);
    List findAllTeachers();
    Teacher findTeacherById(Long idTeacher);
    Teacher findByName(String name);
}
