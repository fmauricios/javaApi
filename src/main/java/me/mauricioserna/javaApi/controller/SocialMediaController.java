package me.mauricioserna.javaApi.controller;

import me.mauricioserna.javaApi.model.SocialMedia;
import me.mauricioserna.javaApi.service.SocialMediaService;
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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public ResponseEntity<List<SocialMedia>> getSocialMedias(@RequestParam(value = "name", required = false) String name) {
        List<SocialMedia> socialMedias = new ArrayList<>();

        if (name == null) {
            socialMedias = _socialMediaService.findAllSocialMedias();

            if (socialMedias.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<List<SocialMedia>>(socialMedias, HttpStatus.OK);
        } else {
            SocialMedia socialMedia = _socialMediaService.findByName(name);

            if (socialMedia == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

            socialMedias.add(socialMedia);
            return new ResponseEntity<List<SocialMedia>>(socialMedias, HttpStatus.OK);
        }
    }

    /**
     * GET socialMedia
     */

    @RequestMapping(value = "/socialMedias/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<SocialMedia> getSocialMediaById(@PathVariable("id") Long idSocialMedia) {

        if (idSocialMedia == null || idSocialMedia <= 0) {
            return new ResponseEntity(new CustomErrorType("idSocialMedia is required"), HttpStatus.CONFLICT);
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
            return new ResponseEntity(new CustomErrorType("SocialMedia name is required"), HttpStatus.CONFLICT);
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
            return new ResponseEntity(new CustomErrorType("idSocialMedia is required"), HttpStatus.CONFLICT);
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
            return new ResponseEntity(new CustomErrorType("idSocialMedia is required"), HttpStatus.CONFLICT);
        }

        SocialMedia socialMedia = _socialMediaService.findSocialMediaById(idSocialMedia);

        if (socialMedia == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        _socialMediaService.deleteSocialMedia(idSocialMedia);

        return new ResponseEntity<SocialMedia>(HttpStatus.OK);
    }

    public static final String SOCIAL_MEDIA_UPLOADED_FOLDER = "images/socialMedias/";

    /**
     * CREATE socialMedia image
     */

    @RequestMapping(value = "/socialMedias/images", method = RequestMethod.POST, headers = ("content-type=multipart/form-data"))
    public ResponseEntity<byte[]> uploadSocialMediaIcon(@RequestParam("id_social_media") Long id_social_media,
                                                         @RequestParam("file") MultipartFile multipartFile,
                                                         UriComponentsBuilder uriComponentsBuilder) {

        if (id_social_media == null) {
            return new ResponseEntity(new CustomErrorType("idSocialMedia is required"), HttpStatus.NO_CONTENT);
        }

        if (multipartFile.isEmpty()) {
            return new ResponseEntity(new CustomErrorType("File is empty, please select a file to upload"), HttpStatus.NO_CONTENT);
        }

        SocialMedia socialMedia = _socialMediaService.findSocialMediaById(id_social_media);

        if (socialMedia == null) {
            return new ResponseEntity(new CustomErrorType("idSocialMedia does not exists"), HttpStatus.NOT_FOUND);
        }

        if (!socialMedia.getIcon().isEmpty() || socialMedia.getIcon() != null) {
            String fileName = socialMedia.getIcon();
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
            String fileName = String.valueOf(id_social_media) + "-pictureSocialMedia" + dateName + "." + multipartFile.getContentType().split("/")[1];

            socialMedia.setIcon(SOCIAL_MEDIA_UPLOADED_FOLDER + fileName);

            byte[] bytesImage = multipartFile.getBytes();
            Path path = Paths.get(SOCIAL_MEDIA_UPLOADED_FOLDER + fileName);
            Files.write(path, bytesImage);

            _socialMediaService.updateSocialMedia(socialMedia);

            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytesImage);

        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity(new CustomErrorType("Upload failed: " + multipartFile.getOriginalFilename()), HttpStatus.CONFLICT);
        }
    }
}
