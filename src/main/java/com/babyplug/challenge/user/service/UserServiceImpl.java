package com.babyplug.challenge.user.service;

import com.babyplug.challenge.user.domain.User;
import com.babyplug.challenge.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findByUsernameAndDeleted(String username, boolean deleted) {
        return userRepository.findByUsernameAndDeleted(username.trim(), false);
    }
}
