package com.babyplug.challenge.album.utils;

import com.babyplug.challenge.album.domain.Album;
import com.babyplug.challenge.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class AlbumSpecUtils {

    public static Specification<Album> equalBoolean(String field, Boolean value) {
        return (Specification<Album>) (root, criteriaQuery, cb) -> {
            if (value != null) {
                return cb.equal(root.<Boolean>get(field), value);
            }
            return null;
        };
    }

    public static Specification<Album> likeString(String field, String value) {
        return (Specification<Album>) (root, criteriaQuery, cb) -> {
            if (StringUtils.isNotBlank(value)) {
                return SqlUtils.ignorecaseLikeString(cb, root.get(field), value);
            }
            return null;
        };
    }

}
