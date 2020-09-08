package com.babyplug.challenge.photo_metadata.service;

import com.babyplug.challenge.core.exception.NotFoundException;
import com.babyplug.challenge.photo_metadata.bean.PhotoMetadataReqForm;
import com.babyplug.challenge.photo_metadata.bean.PhotoMetadataReqParams;
import com.babyplug.challenge.photo_metadata.domain.PhotoMetadata;
import org.springframework.data.domain.Page;

public interface PhotoMetadataService {
    Page<PhotoMetadata> getAllPages(PhotoMetadataReqParams params);

    PhotoMetadata createPhotoMetadata(PhotoMetadataReqForm form);

    PhotoMetadata getPhotoMetadataById(Long id) throws NotFoundException;

    PhotoMetadata updatePhotoMetadataById(Long id, PhotoMetadataReqForm form) throws NotFoundException;

    void deletePhotoMetadataById(Long id) throws NotFoundException;
}
