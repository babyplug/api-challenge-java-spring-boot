package com.babyplug.challenge.album.service;

import com.babyplug.challenge.album.bean.AlbumReqForm;
import com.babyplug.challenge.album.bean.AlbumReqParams;
import com.babyplug.challenge.album.domain.Album;
import com.babyplug.challenge.core.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface AlbumService {
    Page<Album> getAllPages(AlbumReqParams params);

    Album createAlbum(AlbumReqForm form);

    Album getAlbumById(Long id) throws NotFoundException;

    Album updateAlbumById(Long id, AlbumReqForm form) throws NotFoundException;

    void deleteAlbumById(Long id) throws NotFoundException;
}
