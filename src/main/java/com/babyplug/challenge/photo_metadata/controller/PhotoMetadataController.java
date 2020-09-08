package com.babyplug.challenge.photo_metadata.controller;

import com.babyplug.challenge.constant.SystemConstant;
import com.babyplug.challenge.core.ProcessResult;
import com.babyplug.challenge.core.exception.ErrorMessage;
import com.babyplug.challenge.core.exception.NotFoundException;
import com.babyplug.challenge.photo_metadata.bean.PhotoMetadataReqForm;
import com.babyplug.challenge.photo_metadata.bean.PhotoMetadataReqParams;
import com.babyplug.challenge.photo_metadata.domain.PhotoMetadata;
import com.babyplug.challenge.photo_metadata.service.PhotoMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/photo-metadata")
public class PhotoMetadataController {

    @Autowired
    private PhotoMetadataService photoMetadataService;

    @GetMapping("")
    public Page<PhotoMetadata> getAllPages(PhotoMetadataReqParams params) {
        return photoMetadataService.getAllPages(params);
    }

    @PostMapping("")
    public ResponseEntity<?> createPhotoMetadata(@RequestBody PhotoMetadataReqForm form) {
        ResponseEntity<?> response;

        PhotoMetadata photoMetadata = photoMetadataService.createPhotoMetadata(form);
        response = ResponseEntity.status(HttpStatus.CREATED).body(photoMetadata);

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPhotoMetadataById(@PathVariable(name = "id") Long id) {
        ResponseEntity<?> response;
        ProcessResult processResult;

        try {
            PhotoMetadata photoMetadata = photoMetadataService.getPhotoMetadataById(id);
            response = ResponseEntity.ok(photoMetadata);
        } catch (NotFoundException e) {
            processResult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(processResult);
        }

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePhotoMetadataById(@PathVariable(name = "id") Long id, @RequestBody PhotoMetadataReqForm form) {
        ResponseEntity<?> response;
        ProcessResult processResult;

        try {
            PhotoMetadata photoMetadata = photoMetadataService.updatePhotoMetadataById(id, form);
            response = ResponseEntity.ok(photoMetadata);
        } catch (NotFoundException e) {
            processResult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(processResult);
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePhotoMetadataById(@PathVariable(name = "id") Long id) {
        ResponseEntity<?> response;
        ProcessResult processResult;

        try {
            photoMetadataService.deletePhotoMetadataById(id);
            response = ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            processResult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(processResult);
        }

        return response;
    }

}
