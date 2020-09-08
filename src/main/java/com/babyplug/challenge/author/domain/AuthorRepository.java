package com.babyplug.challenge.author.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    Page<Author> findAll(Specification<Author> specification, Pageable pageable);

    List<Author> findAll(Specification<Author> specification);

    List<Author> findAll(Specification<Author> specification, Sort sort);

    Optional<Author> findByIdAndDeleted(Long id, boolean deleted);

}
