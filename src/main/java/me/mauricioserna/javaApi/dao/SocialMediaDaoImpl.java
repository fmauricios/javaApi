package me.mauricioserna.javaApi.dao;

import me.mauricioserna.javaApi.model.SocialMedia;
import me.mauricioserna.javaApi.model.TeacherSocialMedia;

import java.util.List;

/**
 * Created by mauricio on 12/06/17.
 */

public class SocialMediaDaoImpl extends AbstractSession implements SocialMediaDao {
    @Override
    public void saveSocialMedia(SocialMedia socialMedia) {
        getSession().persist(socialMedia);
    }

    @Override
    public void deleteSocialMedia(Long idSocialMedia) {
        SocialMedia socialMedia = findSocialMediaById(idSocialMedia);

        if (socialMedia != null) {
            getSession().delete(socialMedia);
        }
    }

    @Override
    public void updateSocialMedia(SocialMedia socialMedia) {
        getSession().update(socialMedia);
    }

    @Override
    public List<SocialMedia> findAllSocialMedias() {
        return getSession().createQuery("from SocialMedia").list();
    }

    @Override
    public SocialMedia findSocialMediaById(Long idSocialMedia) {
        return getSession().get(SocialMedia.class, idSocialMedia);
    }

    @Override
    public SocialMedia findByName(String name) {
        return null;
    }

    @Override
    public TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String nickname) {
        return null;
    }
}
