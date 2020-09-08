package com.babyplug.challenge.user.service;

import com.babyplug.challenge.core.exception.NotFoundException;
import com.babyplug.challenge.core.exception.UnProcessAbleException;
import com.babyplug.challenge.user.bean.ChangePasswordReqForm;
import com.babyplug.challenge.user.bean.UserReqForm;
import com.babyplug.challenge.user.bean.UserReqParams;
import com.babyplug.challenge.user.domain.User;
import com.sun.istack.NotNull;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<User> findByUsernameAndDeleted(String username, boolean deleted);

    Page<User> getAllPages(UserReqParams params);

    User createUser(UserReqForm form) throws UnProcessAbleException;

    User getUserById(@NotNull Long id) throws NotFoundException;

    User updateUserById(@NotNull Long id, UserReqForm form) throws NotFoundException;

    void deleteUserById(@NotNull Long id) throws NotFoundException;

    User getUserProfile();

    User updateUserProfile(UserReqForm form);

    void changePasswordByAdmin(@NotNull Long userId, ChangePasswordReqForm form) throws NotFoundException, UnProcessAbleException;

    void changePassword(ChangePasswordReqForm form) throws UnProcessAbleException;
}
