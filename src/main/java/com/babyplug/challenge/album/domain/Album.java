package com.babyplug.challenge.album.domain;

import com.babyplug.challenge.core.BaseEntity;
import com.babyplug.challenge.photo.domain.Photo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Album extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "album_photos_photo",
            joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "photo_id", referencedColumnName = "id")
    )
    private List<Photo> photoList;

    @JsonIgnore
    private Boolean deleted;

    private Long createdBy;
    private Long updatedBy;

}
