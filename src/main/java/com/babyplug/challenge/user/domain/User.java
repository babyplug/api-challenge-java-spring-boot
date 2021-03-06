package com.babyplug.challenge.user.domain;

import com.babyplug.challenge.core.BaseEntity;
import com.babyplug.challenge.user.bean.EUserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String displayName;
    private Long age;

    private String username;
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private EUserType userType;
    @Enumerated(EnumType.STRING)
    private EUserStatus status;

    @JsonIgnore
    private Boolean deleted;

    private Long createdBy;
    private Long updatedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
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
