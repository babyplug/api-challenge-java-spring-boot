package com.babyplug.challenge.photo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends CrudRepository<Photo, Long> {

    Page<Photo> findAll(Specification<Photo> specification, Pageable pageable);

    List<Photo> findAll(Specification<Photo> specification);

    List<Photo> findAll(Specification<Photo> specification, Sort sort);

    Optional<Photo> findByIdAndDeleted(Long id, boolean deleted);

}
