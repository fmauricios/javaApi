package me.mauricioserna.javaApi.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by mauricio on 11/06/17.
 */

@Entity
@Table(name = "teacher_social_media")
public class TeacherSocialMedia implements Serializable {

    @Id
    @Column(name = "id_teacher_social_media")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTeacherSocialMedia;

    @Column(name = "nickname")
    private String nickname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_teacher")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_social_media")
    private SocialMedia socialMedia;

    public TeacherSocialMedia() { super(); }

    public TeacherSocialMedia(Teacher teacher, SocialMedia socialMedia, String nickname) {
        this.teacher = teacher;
        this.socialMedia = socialMedia;
        this.nickname = nickname;
    }

    public Long getIdTeacherSocialMedia() { return idTeacherSocialMedia; }

    public void setIdTeacherSocialMedia(Long idTeacherSocialMedia) {
        this.idTeacherSocialMedia = idTeacherSocialMedia;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public SocialMedia getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(SocialMedia socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
