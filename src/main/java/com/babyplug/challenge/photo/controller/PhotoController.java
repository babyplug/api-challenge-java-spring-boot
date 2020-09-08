package com.babyplug.challenge.photo.controller;

import com.babyplug.challenge.photo.bean.PhotoReqForm;
import com.babyplug.challenge.photo.bean.PhotoReqParams;
import com.babyplug.challenge.photo.domain.Photo;
import com.babyplug.challenge.photo.service.PhotoService;
import com.babyplug.challenge.constant.SystemConstant;
import com.babyplug.challenge.core.ProcessResult;
import com.babyplug.challenge.core.exception.ErrorMessage;
import com.babyplug.challenge.core.exception.NotFoundException;
import com.babyplug.challenge.photo.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("")
    public Page<Photo> getAllPages(PhotoReqParams params) {
        return photoService.getAllPages(params);
    }

    @PostMapping("")
    public ResponseEntity<?> createPhoto(@RequestBody PhotoReqForm form) {
        ResponseEntity<?> response;

        Photo photo = photoService.createPhoto(form);
        response = ResponseEntity.status(HttpStatus.CREATED).body(photo);

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPhotoById(@PathVariable(name = "id") Long id) {
        ResponseEntity<?> response;
        ProcessResult processResult;

        try {
            Photo photo = photoService.getPhotoById(id);
            response = ResponseEntity.ok(photo);
        } catch (NotFoundException e) {
            processResult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(processResult);
        }

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePhotoById(@PathVariable(name = "id") Long id, @RequestBody PhotoReqForm form) {
        ResponseEntity<?> response;
        ProcessResult processResult;

        try {
            Photo photo = photoService.updatePhotoById(id, form);
            response = ResponseEntity.ok(photo);
        } catch (NotFoundException e) {
            processResult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(processResult);
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePhotoById(@PathVariable(name = "id") Long id) {
        ResponseEntity<?> response;
        ProcessResult processResult;

        try {
            photoService.deletePhotoById(id);
            response = ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            processResult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(processResult);
        }

        return response;
    }

}
