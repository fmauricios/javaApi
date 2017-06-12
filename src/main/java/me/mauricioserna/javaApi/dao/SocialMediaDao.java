package me.mauricioserna.javaApi.dao;

import me.mauricioserna.javaApi.model.SocialMedia;
import me.mauricioserna.javaApi.model.TeacherSocialMedia;

import java.util.List;

/**
 * Created by mauricio on 12/06/17.
 */
public interface SocialMediaDao {
    void saveSocialMedia(SocialMedia socialMedia);
    void deleteSocialMedia(Long idSocialMedia);
    void updateSocialMedia(SocialMedia socialMedia);
    List findAllSocialMedias();
    SocialMedia findSocialMediaById(Long idSocialMedia);
    SocialMedia findByName(String name);
    TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String nickname);
}
