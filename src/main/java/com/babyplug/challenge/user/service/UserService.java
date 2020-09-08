package com.babyplug.challenge.user.service;

import com.babyplug.challenge.user.domain.User;

import java.util.List;

public interface UserService {
    List<User> findByUsernameAndDeleted(String username, boolean deleted);
}
