package me.mauricioserna.javaApi.service;

import me.mauricioserna.javaApi.dao.SocialMediaDao;
import me.mauricioserna.javaApi.model.SocialMedia;
import me.mauricioserna.javaApi.model.TeacherSocialMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by mauricio on 12/06/17.
 */

@Service("socialMediaService")
@Transactional
public class SocialMediaServiceImpl implements SocialMediaService {

    @Autowired
    private SocialMediaDao _socialMediaDao;

    @Override
    public void saveSocialMedia(SocialMedia socialMedia) {
        _socialMediaDao.saveSocialMedia(socialMedia);
    }

    @Override
    public void deleteSocialMedia(Long idSocialMedia) {
        _socialMediaDao.deleteSocialMedia(idSocialMedia);
    }

    @Override
    public void updateSocialMedia(SocialMedia socialMedia) {
        _socialMediaDao.updateSocialMedia(socialMedia);
    }

    @Override
    public List findAllSocialMedias() {
        return _socialMediaDao.findAllSocialMedias();
    }

    @Override
    public SocialMedia findSocialMediaById(Long idSocialMedia) {
        return _socialMediaDao.findSocialMediaById(idSocialMedia);
    }

    @Override
    public SocialMedia findByName(String name) {
        return _socialMediaDao.findByName(name);
    }

    @Override
    public TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String nickname) {
        return null;
    }
}
