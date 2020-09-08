package com.babyplug.challenge.photo_metadata.service;

import com.babyplug.challenge.constant.SystemConstant;
import com.babyplug.challenge.core.exception.NotFoundException;
import com.babyplug.challenge.photo_metadata.bean.PhotoMetadataReqForm;
import com.babyplug.challenge.photo_metadata.bean.PhotoMetadataReqParams;
import com.babyplug.challenge.photo_metadata.domain.PhotoMetadata;
import com.babyplug.challenge.photo_metadata.domain.PhotoMetadataRepository;
import com.babyplug.challenge.photo_metadata.utils.PhotoMetadataSpecUtils;
import com.babyplug.challenge.security.configuration.service.AuthenticationFacade;
import com.babyplug.challenge.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhotoMetadataServiceImpl implements PhotoMetadataService {

    @Autowired
    private PhotoMetadataRepository photoMetadataRepository;

    @Autowired
    private AuthenticationFacade authFacade;

    @Override
    public Page<PhotoMetadata> getAllPages(PhotoMetadataReqParams params) {
        Specification<PhotoMetadata> specification = prepareSpec(params);

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int size = params.getSize() != null ? params.getSize() : SystemConstant.PAGE_SIZE;
        if (params.isShowAll()) {
            size = SystemConstant.PAGE_MAX;
        }
        Pageable pageable = PageRequest.of(params.getPage(), size, sort);
        return photoMetadataRepository.findAll(specification, pageable);
    }

    private Specification<PhotoMetadata> prepareSpec(PhotoMetadataReqParams params) {
        Specification<PhotoMetadata> deleted = PhotoMetadataSpecUtils.equalBoolean("deleted", false);

        return Specification.where(deleted);
    }

    @Override
    public PhotoMetadata createPhotoMetadata(PhotoMetadataReqForm form) {
        User user = authFacade.getAuthentication();

        PhotoMetadata dto = new PhotoMetadata();
        dto.setDeleted(false);
        dto.setCreatedBy(user.getId());

        dto.setHeight(form.getHeight());
        dto.setWidth(form.getWidth());
        dto.setOrientation(form.getOrientation());
        dto.setCompressed(form.getCompressed());
        dto.setComment(form.getComment());
        dto.setPhotoId(form.getPhotoId());

        return photoMetadataRepository.save(dto);
    }

    @Override
    public PhotoMetadata getPhotoMetadataById(Long id) throws NotFoundException {
        Optional<PhotoMetadata> photoMetadataOptional = photoMetadataRepository.findByIdAndDeleted(id, false);
        if (photoMetadataOptional.isEmpty()) {
            throw new NotFoundException("ENOTFOUND", "Can't find by this id");
        }
        return photoMetadataOptional.get();
    }

    @Override
    public PhotoMetadata updatePhotoMetadataById(Long id, PhotoMetadataReqForm form) throws NotFoundException {
        User user = authFacade.getAuthentication();

        PhotoMetadata dto = getPhotoMetadataById(id);
        dto.setDeleted(false);
        dto.setCreatedBy(user.getId());

        dto.setHeight(form.getHeight());
        dto.setWidth(form.getWidth());
        dto.setOrientation(form.getOrientation());
        dto.setCompressed(form.getCompressed());
        dto.setComment(form.getComment());
        dto.setPhotoId(form.getPhotoId());

        return photoMetadataRepository.save(dto);
    }

    @Override
    public void deletePhotoMetadataById(Long id) throws NotFoundException {
        User user = authFacade.getAuthentication();
        PhotoMetadata dto = getPhotoMetadataById(id);
        dto.setUpdatedBy(user.getId());
        dto.setDeleted(true);
        photoMetadataRepository.save(dto);
    }
}
