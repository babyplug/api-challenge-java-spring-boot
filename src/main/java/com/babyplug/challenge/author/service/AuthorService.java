package com.babyplug.challenge.author.service;

import com.babyplug.challenge.author.bean.AuthorReqForm;
import com.babyplug.challenge.author.bean.AuthorReqParams;
import com.babyplug.challenge.author.domain.Author;
import com.babyplug.challenge.core.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface AuthorService {
    Page<Author> getAllPages(AuthorReqParams params);

    Author createAuthor(AuthorReqForm form);

    Author getAuthorById(Long id) throws NotFoundException;

    Author updateAuthorById(Long id, AuthorReqForm form) throws NotFoundException;

    void deleteAuthorById(Long id) throws NotFoundException;
}
