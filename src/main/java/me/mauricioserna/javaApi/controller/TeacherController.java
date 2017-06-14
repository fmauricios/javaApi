package me.mauricioserna.javaApi.controller;

import me.mauricioserna.javaApi.model.Teacher;
import me.mauricioserna.javaApi.service.TeacherService;
import me.mauricioserna.javaApi.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import sun.rmi.runtime.Log;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            return new ResponseEntity(new CustomErrorType("idTeacher is required"), HttpStatus.NO_CONTENT);
        }

        Teacher teacher = _teacherService.findTeacherById(idTeacher);

        if (teacher == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        _teacherService.deleteTeacher(idTeacher);

        return new ResponseEntity<Teacher>(HttpStatus.OK);
    }

    public static final String TEACHER_UPLOADED_FOLDER = "images/teachers/";

    /**
     * CREATE teacher image
     */

    @RequestMapping(value = "/teachers/image", method = RequestMethod.POST, headers = ("content-type=multipart/form-data"))
    public ResponseEntity<byte[]> uploadTeacherImage(@RequestParam("id_teacher") Long id_teacher,
                                                     @RequestParam("file") MultipartFile multipartFile,
                                                     UriComponentsBuilder uriComponentsBuilder) {

        if (id_teacher == null) {
            return new ResponseEntity(new CustomErrorType("idTeacher is required"), HttpStatus.NO_CONTENT);
        }

        if (multipartFile.isEmpty()) {
            return new ResponseEntity(new CustomErrorType("File is empty, please select a file to upload"), HttpStatus.NO_CONTENT);
        }

        Teacher teacher = _teacherService.findTeacherById(id_teacher);

        if (teacher == null) {
            return new ResponseEntity(new CustomErrorType("idTeacher does not exists"), HttpStatus.NOT_FOUND);
        }

        if (!teacher.getAvatar().isEmpty() || teacher.getAvatar() != null) {
            String fileName = teacher.getAvatar();
            Path path = Paths.get(fileName);
            File file = path.toFile();

            if (file.exists()) {
                file.delete();
            }
        }

        try {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd-HH-mm-ss");
            String dateName = dateFormat.format(date);
            String fileName = String.valueOf(id_teacher) + "-pictureTeacher" + dateName + "." + multipartFile.getContentType().split("/")[1];

            teacher.setAvatar(TEACHER_UPLOADED_FOLDER + fileName);

            byte[] bytesImage = multipartFile.getBytes();
            Path path = Paths.get(TEACHER_UPLOADED_FOLDER + fileName);
            Files.write(path, bytesImage);

            _teacherService.updateTeacher(teacher);

            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytesImage);

        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity(new CustomErrorType("Upload failed: " + multipartFile.getOriginalFilename()), HttpStatus.CONFLICT);
        }
    }
}
