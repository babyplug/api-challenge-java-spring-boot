package com.babyplug.challenge.author.controller;

import com.babyplug.challenge.author.bean.AuthorReqForm;
import com.babyplug.challenge.author.bean.AuthorReqParams;
import com.babyplug.challenge.author.domain.Author;
import com.babyplug.challenge.author.service.AuthorService;
import com.babyplug.challenge.constant.SystemConstant;
import com.babyplug.challenge.core.ProcessResult;
import com.babyplug.challenge.core.exception.ErrorMessage;
import com.babyplug.challenge.core.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("")
    public Page<Author> getAllPages(AuthorReqParams params) {
        return authorService.getAllPages(params);
    }

    @PostMapping("")
    public ResponseEntity<?> createAuthor(@RequestBody AuthorReqForm form) {
        ResponseEntity<?> response;

        Author author = authorService.createAuthor(form);
        response = ResponseEntity.status(HttpStatus.CREATED).body(author);

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable(name = "id") Long id) {
        ResponseEntity<?> response;
        ProcessResult processResult;

        try {
            Author author = authorService.getAuthorById(id);
            response = ResponseEntity.ok(author);
        } catch (NotFoundException e) {
            processResult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(processResult);
        }

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthorById(@PathVariable(name = "id") Long id, @RequestBody AuthorReqForm form) {
        ResponseEntity<?> response;
        ProcessResult processResult;

        try {
            Author author = authorService.updateAuthorById(id, form);
            response = ResponseEntity.ok(author);
        } catch (NotFoundException e) {
            processResult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(processResult);
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable(name = "id") Long id) {
        ResponseEntity<?> response;
        ProcessResult processResult;

        try {
            authorService.deleteAuthorById(id);
            response = ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            processResult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(processResult);
        }

        return response;
    }

}
