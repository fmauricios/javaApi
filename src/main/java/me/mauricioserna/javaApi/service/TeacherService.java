package me.mauricioserna.javaApi.service;

import me.mauricioserna.javaApi.model.Teacher;

import java.util.List;

/**
 * Created by mauricio on 12/06/17.
 */
public interface TeacherService {
    void saveTeacher(Teacher teacher);
    void deleteTeacher(Long idTeacher);
    void updateTeacher(Teacher teacher);
    List findAllTeachers();
    Teacher findTeacherById(Long idTeacher);
    Teacher findByName(String name);
}
