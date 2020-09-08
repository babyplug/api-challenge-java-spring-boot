package com.babyplug.challenge.user.service;

import com.babyplug.challenge.constant.SystemConstant;
import com.babyplug.challenge.core.exception.NotFoundException;
import com.babyplug.challenge.core.exception.UnProcessAbleException;
import com.babyplug.challenge.security.configuration.service.AuthenticationFacade;
import com.babyplug.challenge.user.bean.ChangePasswordReqForm;
import com.babyplug.challenge.user.bean.EUserStatus;
import com.babyplug.challenge.user.bean.UserReqForm;
import com.babyplug.challenge.user.bean.UserReqParams;
import com.babyplug.challenge.user.domain.EUserType;
import com.babyplug.challenge.user.domain.User;
import com.babyplug.challenge.user.domain.UserRepository;
import com.babyplug.challenge.user.utils.UserSpecUtils;
import com.sun.istack.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationFacade authFacade;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    private String passwordEncoder(@NotNull String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public List<User> findByUsernameAndDeleted(@NotNull String username, boolean deleted) {
        return userRepository.findByUsernameAndDeleted(username.trim(), false);
    }

    @Override
    public Page<User> getAllPages(UserReqParams params) {
        Specification<User> specification = prepareSpec(params);

        Sort sort = Sort.by(Sort.Direction.DESC, "userId");
        int size = params.getSize() != null ? params.getSize() : SystemConstant.PAGE_SIZE;
        if (params.isShowAll()) {
            size = SystemConstant.PAGE_MAX;
        }
        Pageable pageable = PageRequest.of(params.getPage(), size, sort);
        return userRepository.findAll(specification, pageable);
    }

    private Specification<User> prepareSpec(UserReqParams params) {
        Specification<User> deleted = UserSpecUtils.equalBoolean("deleted", false);
        Specification<User> username = UserSpecUtils.likeStringIgnoreCase("username", params.getUsername());
        Specification<User> firstName = UserSpecUtils.likeStringIgnoreCase("firstName", params.getFirstName());
        Specification<User> lastName = UserSpecUtils.likeStringIgnoreCase("lastName", params.getLastName());
        Specification<User> displayName = UserSpecUtils.likeStringIgnoreCase("displayName", params.getDisplayName());

        Specification<User> age = UserSpecUtils.equalLong("age", params.getAge());

        if (params.getStatus() == null) params.setStatus(EUserStatus.ACTIVE);
        Specification<User> status = UserSpecUtils.equalStatus(params.getStatus());

        // filter current user
        User cUser = authFacade.getAuthentication();
        Specification<User> exceptCUserId = UserSpecUtils.notEqualLong("userId", cUser.getId());

        return Specification.where(deleted)
                .and(username).and(firstName).and(lastName).and(displayName).and(age)
                .and(status) .and(exceptCUserId);
    }

    @Override
    public User createUser(UserReqForm form) throws UnProcessAbleException {
        validateReq(form, true);
        User currentUser = authFacade.getAuthentication();

        User dto = new User();
        dto.setCreatedBy(currentUser.getId());
        dto.setDeleted(false);

        dto.setFirstName(form.getFirstName());
        dto.setLastName(form.getLastName());
        dto.setDisplayName(form.getDisplayName());
        dto.setAge(form.getAge());
        dto.setUsername(form.getUsername());

        dto.setPassword(passwordEncoder(form.getPassword()));

        dto.setStatus(defaultStatus(form));
        dto.setUserType(defaultUserType(form));

        return userRepository.save(dto);
    }

    private EUserStatus defaultStatus(UserReqForm form) {
        return form.getStatus() != null ? form.getStatus() : EUserStatus.ACTIVE;
    }

    private EUserType defaultUserType(UserReqForm form) {
        return form.getUserType() != null ? form.getUserType() : EUserType.USER;
    }

    private void validateReq(UserReqForm form, boolean isCreate) throws UnProcessAbleException {
        if (isCreate) {
            List<User> users = userRepository.findByUsernameAndDeleted(form.getUsername(), false);
            if(!CollectionUtils.isEmpty(users)) {
                throw new UnProcessAbleException("DUPL_USERNAME", "This username is exists !");
            }
        }
    }

    @Override
    public User getUserById(@NotNull Long id) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findByIdAndDeleted(id, false);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("ENOTFOUND", "Can't found by this id");
        }
        return optionalUser.get();
    }

    @Override
    public User updateUserById(@NotNull Long id, UserReqForm form) throws NotFoundException {
        User currentUser = authFacade.getAuthentication();
        User dto = getUserById(id);
        dto.setUpdatedBy(currentUser.getId());

        dto.setFirstName(form.getFirstName());
        dto.setLastName(form.getLastName());
        dto.setDisplayName(form.getDisplayName());
        dto.setAge(form.getAge());

        dto.setStatus(defaultStatus(form));
        dto.setUserType(defaultUserType(form));

        return userRepository.save(dto);
    }

    @Override
    public void deleteUserById(@NotNull Long id) throws NotFoundException {
        User currentUser = authFacade.getAuthentication();
        User dto = getUserById(id);
        dto.setUpdatedBy(currentUser.getId());
        dto.setDeleted(true);
        userRepository.save(dto);
    }

    @Override
    public User getUserProfile() {
        User currentUser = authFacade.getAuthentication();
        try {
            User userDTO = getUserById(currentUser.getId());
            return userRepository.save(userDTO);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return new User();
    }

    @Override
    public User updateUserProfile(UserReqForm form)  {
        try {
            User currentUser = authFacade.getAuthentication();
            User userDTO = getUserById(currentUser.getId());
            userDTO.setFirstName(form.getFirstName());
            userDTO.setLastName(form.getLastName());
            userDTO.setDisplayName(form.getDisplayName());
            userDTO.setAge(form.getAge());

            return userRepository.save(userDTO);
        } catch (NotFoundException e) {
            return null;
        }
    }

    @Override
    public void changePasswordByAdmin(@NotNull Long userId, ChangePasswordReqForm form) throws NotFoundException, UnProcessAbleException {
        if (StringUtils.isBlank(form.getNewPassword())) {
            throw new UnProcessAbleException("NEW_PASSWORD_REQUIRED", "New password is required !");
        }
        User dto = getUserById(userId);
        dto.setPassword(passwordEncoder(form.getNewPassword()));

        userRepository.save(dto);
    }

    @Override
    public void changePassword(ChangePasswordReqForm form) throws UnProcessAbleException {
        User cUser = authFacade.getAuthentication();

        if (StringUtils.isBlank(form.getOldPassword())) {
            throw new UnProcessAbleException("OLD_PASSWORD_REQUIRED", "Old password is required !");
        }
        if (StringUtils.isBlank(form.getNewPassword())) {
            throw new UnProcessAbleException("NEW_PASSWORD_REQUIRED", "New password is required !");
        }

        try {
            User user = getUserById(cUser.getId());

            boolean isMatch = bCryptPasswordEncoder.matches(form.getOldPassword(), user.getPassword());

            if (!isMatch) throw new UnProcessAbleException("PASSWORD_NOT_MATCH", "Old password not match !");

            user.setPassword(passwordEncoder(form.getNewPassword()));
            userRepository.save(user);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

}
