package com.babyplug.challenge.user.bean;

import com.babyplug.challenge.user.domain.EUserType;

import java.io.Serializable;

public class UserReqForm implements Serializable {
    private String firstName;
    private String lastName;
    private String displayName;
    private Long age;

    private String username;
    private String password;

    private EUserType userType;
    private EUserStatus status;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EUserType getUserType() {
        return userType;
    }

    public void setUserType(EUserType userType) {
        this.userType = userType;
    }

    public EUserStatus getStatus() {
        return status;
    }

    public void setStatus(EUserStatus status) {
        this.status = status;
    }
}
