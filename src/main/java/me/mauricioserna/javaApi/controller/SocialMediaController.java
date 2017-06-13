package me.mauricioserna.javaApi.controller;

import me.mauricioserna.javaApi.model.SocialMedia;
import me.mauricioserna.javaApi.service.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mauricio on 12/06/17.
 */

@Controller
@RequestMapping("/v1")
public class SocialMediaController {

    @Autowired
    SocialMediaService _socialMediaService;

    /**
     * GET socialMedias
     */

    @RequestMapping(value = "/socialMedias", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<SocialMedia>> getSocialMedias() {
        List<SocialMedia> socialMedias = new ArrayList<>();
        socialMedias = _socialMediaService.findAllSocialMedias();

        if (socialMedias.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<SocialMedia>>(socialMedias, HttpStatus.OK);
    }

    /**
     * GET socialMedia
     */

    @RequestMapping(value = "/socialMedias/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<SocialMedia> getSocialMediaById(@PathVariable("id") Long idSocialMedia) {

        if (idSocialMedia == null || idSocialMedia <= 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        SocialMedia socialMedia = _socialMediaService.findSocialMediaById(idSocialMedia);

        if (socialMedia == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<SocialMedia>(socialMedia, HttpStatus.OK);
    }

    /**
     * POST
     */

    @RequestMapping(value = "/socialMedias", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> createSocialMedia(@RequestBody SocialMedia socialMedia, UriComponentsBuilder uriComponentsBuilder) {

        if (socialMedia.getName().equals(null) || socialMedia.getName().isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        if (_socialMediaService.findByName(socialMedia.getName()) != null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        _socialMediaService.saveSocialMedia(socialMedia);
        SocialMedia socialMedia1 = _socialMediaService.findByName(socialMedia.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                uriComponentsBuilder
                        .path("/v1/socialMedias/{id}")
                        .buildAndExpand(socialMedia1.getIdSocialMedia())
                        .toUri()
        );

        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    /**
     * UPDATE
     */
    @RequestMapping(value = "/socialMedias/{id}", method = RequestMethod.PATCH, headers = "Accept=application/json")
    public ResponseEntity<SocialMedia> updateSocialMedia(@PathVariable("id") Long idSocialMedia, @RequestBody SocialMedia socialMedia) {

        if (idSocialMedia == null || idSocialMedia <= 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        SocialMedia currentSocialMedia = _socialMediaService.findSocialMediaById(idSocialMedia);

        if (currentSocialMedia == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        currentSocialMedia.setName(socialMedia.getName());
        currentSocialMedia.setIcon(socialMedia.getIcon());

        _socialMediaService.updateSocialMedia(currentSocialMedia);

        return new ResponseEntity<SocialMedia>(currentSocialMedia, HttpStatus.OK);
    }

    /**
     * DELETE
     */
    @RequestMapping(value = "/socialMedias/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<SocialMedia> deleteSocialMedia(@PathVariable("id") Long idSocialMedia) {
        if (idSocialMedia == null || idSocialMedia <= 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        SocialMedia socialMedia = _socialMediaService.findSocialMediaById(idSocialMedia);

        if (socialMedia == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        _socialMediaService.deleteSocialMedia(idSocialMedia);

        return new ResponseEntity<SocialMedia>(HttpStatus.OK);
    }

}
