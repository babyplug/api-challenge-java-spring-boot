package com.babyplug.challenge.photo_metadata.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoMetadataRepository extends CrudRepository<PhotoMetadata, Long> {

    Page<PhotoMetadata> findAll(Specification<PhotoMetadata> specification, Pageable pageable);

    List<PhotoMetadata> findAll(Specification<PhotoMetadata> specification);

    List<PhotoMetadata> findAll(Specification<PhotoMetadata> specification, Sort sort);

    Optional<PhotoMetadata> findByIdAndDeleted(Long id, boolean deleted);

}
