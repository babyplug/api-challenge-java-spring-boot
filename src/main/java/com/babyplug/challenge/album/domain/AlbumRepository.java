package com.babyplug.challenge.album.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends CrudRepository<Album, Long> {

    Page<Album> findAll(Specification<Album> specification, Pageable pageable);

    List<Album> findAll(Specification<Album> specification);

    List<Album> findAll(Specification<Album> specification, Sort sort);

    Optional<Album> findByIdAndDeleted(Long id, boolean deleted);

}
