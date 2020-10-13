package com.babyplug.challenge.album.controller;

import com.babyplug.challenge.album.bean.AlbumReqForm;
import com.babyplug.challenge.album.bean.AlbumReqParams;
import com.babyplug.challenge.album.domain.Album;
import com.babyplug.challenge.album.service.AlbumService;
import com.babyplug.challenge.constant.SystemConstant;
import com.babyplug.challenge.core.ProcessResult;
import com.babyplug.challenge.core.exception.ErrorMessage;
import com.babyplug.challenge.core.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping("")
    public Page<Album> getAllPages(AlbumReqParams params) {
        return albumService.getAllPages(params);
    }

    @PostMapping("")
    public ResponseEntity<?> createAlbum(@RequestBody AlbumReqForm form) {
        ResponseEntity<?> response = null;

        Album album = albumService.createAlbum(form);
        response = ResponseEntity.status(HttpStatus.CREATED).body(album);

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlbumById(@PathVariable(name = "id") Long id) {
        ResponseEntity<?> response = null;
        ProcessResult processResult = null;

        try {
            Album activity = albumService.getAlbumById(id);
            response = ResponseEntity.ok(activity);
        } catch (NotFoundException e) {
            processResult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(processResult);
        }

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAlbumById(@PathVariable(name = "id") Long id, @RequestBody AlbumReqForm form) {
        ResponseEntity<?> response = null;
        ProcessResult processResult = null;

        try {
            Album activity = albumService.updateAlbumById(id, form);
            response = ResponseEntity.ok(activity);
        } catch (NotFoundException e) {
            processResult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(processResult);
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbumById(@PathVariable(name = "id") Long id) {
        ResponseEntity<?> response = null;
        ProcessResult processResult = null;

        try {
            albumService.deleteAlbumById(id);
            response = ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            processResult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(processResult);
        }

        return response;
    }

}
