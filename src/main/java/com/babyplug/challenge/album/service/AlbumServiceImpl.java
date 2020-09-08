package com.babyplug.challenge.album.service;

import com.babyplug.challenge.album.bean.AlbumReqForm;
import com.babyplug.challenge.album.bean.AlbumReqParams;
import com.babyplug.challenge.album.domain.Album;
import com.babyplug.challenge.album.domain.AlbumRepository;
import com.babyplug.challenge.album.utils.AlbumSpecUtils;
import com.babyplug.challenge.constant.SystemConstant;
import com.babyplug.challenge.core.exception.NotFoundException;
import com.babyplug.challenge.security.configuration.service.AuthenticationFacade;
import com.babyplug.challenge.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService{

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AuthenticationFacade authFacade;

    @Override
    public Page<Album> getAllPages(AlbumReqParams params) {
        Specification<Album> specification = prepareSpec(params);

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int size = params.getSize() != null ? params.getSize() : SystemConstant.PAGE_SIZE;
        if (params.isShowAll()) {
            size = SystemConstant.PAGE_MAX;
        }
        Pageable pageable = PageRequest.of(params.getPage(), size, sort);
        return albumRepository.findAll(specification, pageable);
    }

    private Specification<Album> prepareSpec(AlbumReqParams params) {
        Specification<Album> deleted = AlbumSpecUtils.equalBoolean("deleted", false);
        Specification<Album> name = AlbumSpecUtils.likeString("name", params.getName());

        return Specification.where(deleted);
    }

    @Override
    public Album createAlbum(AlbumReqForm form) {
        User user = authFacade.getAuthentication();

        Album dto = new Album();
        dto.setCreatedBy(user.getId());
        dto.setDeleted(false);

        dto.setName(form.getName());

//        if (!CollectionUtils.isEmpty(form.getPhotoList())) {
//            HashSet<Long> photoSet = new HashSet<>(form.getPhotoList());
//            for(Long photoId: photoSet){
//
//            }
//        }

        return albumRepository.save(dto);
    }

    @Override
    public Album getAlbumById(Long id) throws NotFoundException {
        Optional<Album> optional = albumRepository.findByIdAndDeleted(id, false);
        if (optional.isEmpty()) {
            throw new NotFoundException("ENOTFOUND", "Can't find by id");
        }
        return optional.get();
    }

    @Override
    public Album updateAlbumById(Long id, AlbumReqForm form) throws NotFoundException {
        User user = authFacade.getAuthentication();

        Album dto = getAlbumById(id);
        dto.setUpdatedBy(user.getId());

        dto.setName(form.getName());

//        if (!CollectionUtils.isEmpty(form.getPhotoList())) {
//            HashSet<Long> photoSet = new HashSet<>(form.getPhotoList());
//            for(Long photoId: photoSet){
//
//            }
//        }

        return dto;
    }

    @Override
    public void deleteAlbumById(Long id) throws NotFoundException {
        User user = authFacade.getAuthentication();
        Album dto = getAlbumById(id);
        dto.setUpdatedBy(user.getId());
        dto.setDeleted(true);
        albumRepository.save(dto);
    }

}
