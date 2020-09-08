package com.babyplug.challenge.photo.service;

import com.babyplug.challenge.core.exception.NotFoundException;
import com.babyplug.challenge.photo.bean.PhotoReqForm;
import com.babyplug.challenge.photo.bean.PhotoReqParams;
import com.babyplug.challenge.photo.domain.Photo;
import org.springframework.data.domain.Page;

public interface PhotoService {
    Page<Photo> getAllPages(PhotoReqParams params);

    Photo createPhoto(PhotoReqForm form);

    Photo getPhotoById(Long id) throws NotFoundException;

    Photo updatePhotoById(Long id, PhotoReqForm form) throws NotFoundException;

    void deletePhotoById(Long id) throws NotFoundException;
}
