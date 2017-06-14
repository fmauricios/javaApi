package me.mauricioserna.javaApi.controller;

import me.mauricioserna.javaApi.model.Teacher;
import me.mauricioserna.javaApi.service.TeacherService;
import me.mauricioserna.javaApi.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mauricio on 13/06/17.
 */

@Controller
@RequestMapping("/v1")
public class TeacherController {

    @Autowired
    TeacherService _teacherService;

    /**
     * GET teachers
     */

    @RequestMapping(value = "/teachers", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<Teacher>> getTeachers(@RequestParam(value = "name", required = false) String name) {
        List<Teacher> teachers = new ArrayList<>();

        if (name == null) {
            teachers = _teacherService.findAllTeachers();

            if (teachers.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<List<Teacher>>(teachers, HttpStatus.OK);
        } else {
            Teacher teacher = _teacherService.findByName(name);

            if (teacher == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

            teachers.add(teacher);
            return new ResponseEntity<List<Teacher>>(teachers, HttpStatus.OK);
        }
    }

    /**
     * GET teacher
     */

    @RequestMapping(value = "/teachers/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") Long idTeacher) {

        if (idTeacher == null || idTeacher <= 0) {
            return new ResponseEntity(new CustomErrorType("idTeacher is required"), HttpStatus.CONFLICT);
        }

        Teacher teacher = _teacherService.findTeacherById(idTeacher);

        if (teacher == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
    }

    /**
     * POST - create teacher
     */

    @RequestMapping(value = "/teachers", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> createTeacher(@RequestBody Teacher teacher, UriComponentsBuilder uriComponentsBuilder) {

        if (teacher.getName() == null || teacher.getName().isEmpty()) {
            return new ResponseEntity(new CustomErrorType("Teacher name is required"), HttpStatus.CONFLICT);
        }

        if (_teacherService.findByName(teacher.getName()) != null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        _teacherService.saveTeacher(teacher);
        Teacher teacher1 = _teacherService.findByName(teacher.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                uriComponentsBuilder
                .path("/v1/teachers/{id}")
                .buildAndExpand(teacher1.getIdTeacher())
                .toUri()
        );

        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    /**
     * UPDATE
     */

    @RequestMapping(value = "/teachers/{id}", method = RequestMethod.PATCH, headers = "Accept=application/json")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") Long idTeacher, @RequestBody Teacher teacher) {
        if (idTeacher == null || idTeacher <= 0) {
            return new ResponseEntity(new CustomErrorType("idTeacher is required"), HttpStatus.CONFLICT);
        }

        Teacher currentTeacher = _teacherService.findTeacherById(idTeacher);

        if (currentTeacher == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        currentTeacher.setName(teacher.getName());
        currentTeacher.setAvatar(teacher.getAvatar());

        _teacherService.updateTeacher(currentTeacher);

        return new ResponseEntity<Teacher>(currentTeacher, HttpStatus.OK);
    }

    /**
     * DELETE
     */

    @RequestMapping(value = "/teachers/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable("id") Long idTeacher) {

        if (idTeacher == null || idTeacher <= 0) {
            return new ResponseEntity(new CustomErrorType("idTeacher is required"), HttpStatus.CONFLICT);
        }

        Teacher teacher = _teacherService.findTeacherById(idTeacher);

        if (teacher == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        _teacherService.deleteTeacher(idTeacher);

        return new ResponseEntity<Teacher>(HttpStatus.OK);
    }
}
