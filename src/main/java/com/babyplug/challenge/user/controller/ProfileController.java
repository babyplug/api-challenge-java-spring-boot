package com.babyplug.challenge.user.controller;

import com.babyplug.challenge.constant.SystemConstant;
import com.babyplug.challenge.core.ProcessResult;
import com.babyplug.challenge.core.exception.ErrorMessage;
import com.babyplug.challenge.core.exception.UnProcessAbleException;
import com.babyplug.challenge.user.bean.ChangePasswordReqForm;
import com.babyplug.challenge.user.bean.UserReqForm;
import com.babyplug.challenge.user.domain.User;
import com.babyplug.challenge.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public User getUserProfile() {
        return userService.getUserProfile();
    }

    @PutMapping("/profile")
    public User updateUserProfile(@RequestBody UserReqForm form) {
        return userService.updateUserProfile(form);
    }

    @PutMapping("/profile/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordReqForm form) {
        ResponseEntity<?> response = null;

        try {
            userService.changePassword(form);
            ProcessResult body = new ProcessResult(SystemConstant.PROCESS_SUCCESS, "Change password success");
            response = ResponseEntity.ok(body);
        } catch (UnProcessAbleException e) {
            ProcessResult body = new ProcessResult(SystemConstant.PROCESS_ERROR, new ErrorMessage(e.getCode(), e.getMessage()));
            response = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
        }

        return response;
    }

}
