package me.mauricioserna.javaApi.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by mauricio on 11/06/17.
 */

@Entity
@Table(name = "course")
public class Course implements Serializable {

    @Id
    @Column(name = "id_course")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCourse;

    @Column(name = "name")
    private String name;

    @Column(name = "themes")
    private String themes;

    @Column(name = "project")
    private String project;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_teacher")
    private Teacher teacher;

    public Course() {
        super();
    }

    public Course(String name, String themes, String project) {
        super();
        this.name = name;
        this.themes = themes;
        this.project = project;
    }

    public Long getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Long idCourse) {
        this.idCourse = idCourse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThemes() {
        return themes;
    }

    public void setThemes(String themes) {
        this.themes = themes;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
