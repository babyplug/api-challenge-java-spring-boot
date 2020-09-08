package com.babyplug.challenge.user.controller;

import com.babyplug.challenge.constant.SystemConstant;
import com.babyplug.challenge.core.ProcessResult;
import com.babyplug.challenge.core.exception.ErrorMessage;
import com.babyplug.challenge.core.exception.NotFoundException;
import com.babyplug.challenge.core.exception.UnProcessAbleException;
import com.babyplug.challenge.user.bean.ChangePasswordReqForm;
import com.babyplug.challenge.user.bean.UserReqForm;
import com.babyplug.challenge.user.bean.UserReqParams;
import com.babyplug.challenge.user.domain.User;
import com.babyplug.challenge.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Page<User> getUserPages(UserReqParams params) {
        return userService.getAllPages(params);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserReqForm form) {
        ResponseEntity<?> response = null;
        ProcessResult presult = null;

        User user = null;
        try {
            user = userService.createUser(form);
            response = ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (UnProcessAbleException e) {
            presult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(presult);
        }

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByid(@PathVariable(name = "id") Long id) {
        ResponseEntity<?> response;
        ProcessResult presult;

        User user = null;
        try {
            user = userService.getUserById(id);
            response = ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            presult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(presult);
        }

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") Long id , @RequestBody UserReqForm form) {
        ResponseEntity<?> response;
        ProcessResult presult;

        User user = null;
        try {
            user = userService.updateUserById(id, form);
            response = ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            presult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(presult);
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProcessResult> deleteUser(@PathVariable(name = "id") Long id) {
        ResponseEntity<ProcessResult> response;
        ProcessResult presult;

        try {
            userService.deleteUserById(id);
            response = ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            presult = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(presult);
        }

        return response;
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<?> changePasswordByAdmin(@PathVariable(name = "id") Long userId, @RequestBody ChangePasswordReqForm form) {
        ResponseEntity<?> response = null;

        try {
            userService.changePasswordByAdmin(userId, form);
            ProcessResult body = new ProcessResult(SystemConstant.PROCESS_SUCCESS, "Change password success");
            response = ResponseEntity.ok(body);
        } catch (NotFoundException e) {
            ProcessResult body = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        } catch (UnProcessAbleException e) {
            ProcessResult body = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
        }

        return response;
    }

}
