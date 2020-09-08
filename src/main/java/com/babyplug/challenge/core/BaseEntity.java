package com.babyplug.challenge.core;

import com.babyplug.challenge.constant.SystemConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Column(name = "created_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(timezone = SystemConstant.TIME_ZONE)
    @CreatedDate
    protected Date createdTime;

    @Column(name = "updated_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(timezone = SystemConstant.TIME_ZONE)
    @LastModifiedDate
    protected Date updatedTime;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

}
