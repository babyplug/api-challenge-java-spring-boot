package com.babyplug.challenge.photo.service;

import com.babyplug.challenge.constant.SystemConstant;
import com.babyplug.challenge.core.exception.NotFoundException;
import com.babyplug.challenge.photo.bean.PhotoReqForm;
import com.babyplug.challenge.photo.bean.PhotoReqParams;
import com.babyplug.challenge.photo.domain.Photo;
import com.babyplug.challenge.photo.domain.PhotoRepository;
import com.babyplug.challenge.photo.utils.PhotoSpecUtils;
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
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AuthenticationFacade authFacade;

    @Override
    public Page<Photo> getAllPages(PhotoReqParams params) {
        Specification<Photo> specification = prepareSpec(params);

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int size = params.getSize() != null ? params.getSize() : SystemConstant.PAGE_SIZE;
        if (params.isShowAll()) {
            size = SystemConstant.PAGE_MAX;
        }
        Pageable pageable = PageRequest.of(params.getPage(), size, sort);
        return photoRepository.findAll(specification, pageable);
    }

    private Specification<Photo> prepareSpec(PhotoReqParams params) {
        Specification<Photo> deleted = PhotoSpecUtils.equalBoolean("deleted", false);

        return Specification.where(deleted);
    }

    @Override
    public Photo createPhoto(PhotoReqForm form) {
        User user = authFacade.getAuthentication();

        Photo dto = new Photo();
        if (user != null) {
            dto.setCreatedBy(user.getId());
        }
        dto.setDeleted(false);

        dto.setDescription(form.getDescription());
        dto.setFileName(form.getFileName());
        dto.setViews(form.getViews());
        dto.setIsPublished(form.getPublished());
        dto.setAuthorId(form.getAuthorId());

        return photoRepository.save(dto);
    }

    @Override
    public Photo getPhotoById(Long id) throws NotFoundException {
        Optional<Photo> optional = photoRepository.findByIdAndDeleted(id, false);
        if (optional.isEmpty()) {
            throw new NotFoundException("ENOTFOUND", "Can't find by this id");
        }
        return optional.get();
    }

    @Override
    public Photo updatePhotoById(Long id, PhotoReqForm form) throws NotFoundException {
        User user = authFacade.getAuthentication();

        Photo dto = getPhotoById(id);
        if (user != null) {
            dto.setUpdatedBy(user.getId());
        }

        dto.setDescription(form.getDescription());
        dto.setFileName(form.getFileName());
        dto.setViews(form.getViews());
        dto.setIsPublished(form.getPublished());
        dto.setAuthorId(form.getAuthorId());

        return photoRepository.save(dto);
    }

    @Override
    public void deletePhotoById(Long id) throws NotFoundException {
        User user = authFacade.getAuthentication();
        Photo dto = getPhotoById(id);
        if (user != null) {
            dto.setUpdatedBy(user.getId());
        }
        dto.setDeleted(true);
        photoRepository.save(dto);
    }
}
