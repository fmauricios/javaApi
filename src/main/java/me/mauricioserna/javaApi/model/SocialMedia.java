package me.mauricioserna.javaApi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by mauricio on 11/06/17.
 */


@Entity
@Table(name = "social_media")
public class SocialMedia {

    @Id
    @Column(name = "id_social_media")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSocialMedia;


    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;

    @OneToMany
    @JoinColumn(name = "id_social_media")
    @JsonIgnore
    private Set<TeacherSocialMedia> teacherSocialMedia;

    public SocialMedia() {
        super();
    }

    public SocialMedia(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public Long getIdSocialMedia() {
        return idSocialMedia;
    }

    public void setIdSocialMedia(Long idSocialMedia) {
        this.idSocialMedia = idSocialMedia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<TeacherSocialMedia> getTeacherSocialMedia() {
        return teacherSocialMedia;
    }

    public void setTeacherSocialMedia(Set<TeacherSocialMedia> teacherSocialMedia) {
        this.teacherSocialMedia = teacherSocialMedia;
    }
}
