package com.babyplug.challenge.author.service;

import com.babyplug.challenge.author.bean.AuthorReqForm;
import com.babyplug.challenge.author.bean.AuthorReqParams;
import com.babyplug.challenge.author.domain.Author;
import com.babyplug.challenge.author.domain.AuthorRepository;
import com.babyplug.challenge.author.utils.AuthorSpecUtils;
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

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthenticationFacade authFacade;


    @Override
    public Page<Author> getAllPages(AuthorReqParams params) {
        Specification<Author> specification = prepareSpec(params);

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int size = params.getSize() != null ? params.getSize() : SystemConstant.PAGE_SIZE;
        if (params.isShowAll()) {
            size = SystemConstant.PAGE_MAX;
        }
        Pageable pageable = PageRequest.of(params.getPage(), size, sort);
        return authorRepository.findAll(specification, pageable);
    }

    private Specification<Author> prepareSpec(AuthorReqParams params) {
        Specification<Author> deleted = AuthorSpecUtils.equalBoolean("deleted", false);
        Specification<Author> name = AuthorSpecUtils.likeString("name", params.getName());

        return Specification.where(deleted);
    }

    @Override
    public Author createAuthor(AuthorReqForm form) {
        User user = authFacade.getAuthentication();

        Author dto = new Author();
        dto.setCreatedBy(user.getId());
        dto.setDeleted(false);

        dto.setName(form.getName());
        return authorRepository.save(dto);
    }

    @Override
    public Author getAuthorById(Long id) throws NotFoundException {
        Optional<Author> optional = authorRepository.findByIdAndDeleted(id, false);
        if (optional.isEmpty()) {
            throw new NotFoundException("ENOTFOUND", "Can't find by id");
        }
        return optional.get();
    }

    @Override
    public Author updateAuthorById(Long id, AuthorReqForm form) throws NotFoundException {
        User user = authFacade.getAuthentication();

        Author dto = getAuthorById(id);
        dto.setCreatedBy(user.getId());
        dto.setDeleted(false);

        dto.setName(form.getName());
        return authorRepository.save(dto);
    }

    @Override
    public void deleteAuthorById(Long id) throws NotFoundException {
        User user = authFacade.getAuthentication();
        Author dto = getAuthorById(id);
        dto.setUpdatedBy(user.getId());
        dto.setDeleted(true);
        authorRepository.save(dto);
    }
}
