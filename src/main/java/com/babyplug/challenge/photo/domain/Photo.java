package com.babyplug.challenge.photo.domain;

import com.babyplug.challenge.album.domain.Album;
import com.babyplug.challenge.author.domain.Author;
import com.babyplug.challenge.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Photo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name = "filename")
    private String fileName;
    private Double views;
    private Boolean isPublished;
    private Long authorId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authorId", referencedColumnName = "id", insertable = false, updatable = false)
    private Author author;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "album_photos_photo", joinColumns = @JoinColumn(name = "photo_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"))
    private List<Album> albumList;

    @JsonIgnore
    private Boolean deleted;

    private Long createdBy;
    private Long updatedBy;

}
