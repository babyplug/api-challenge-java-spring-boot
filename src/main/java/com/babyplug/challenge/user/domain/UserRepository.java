package com.babyplug.challenge.user.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Page<User> findAll(Specification<User> specification, Pageable pageable);

    List<User> findAll(Specification<User> specification);

    List<User> findAll(Specification<User> specification, Sort sort);

    Optional<User> findByIdAndDeleted(Long id, boolean deleted);

    List<User> findByUsernameAndDeleted(String username, boolean deleted);
}
