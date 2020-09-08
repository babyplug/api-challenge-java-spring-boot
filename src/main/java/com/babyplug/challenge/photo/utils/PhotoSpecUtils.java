package com.babyplug.challenge.photo.utils;

import com.babyplug.challenge.photo.domain.Photo;
import com.babyplug.challenge.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class PhotoSpecUtils {

    public static Specification<Photo> equalBoolean(String field, Boolean value) {
        return (Specification<Photo>) (root, criteriaQuery, cb) -> {
            if (value != null) {
                return cb.equal(root.<Boolean>get(field), value);
            }
            return null;
        };
    }

    public static Specification<Photo> likeString(String field, String value) {
        return (Specification<Photo>) (root, criteriaQuery, cb) -> {
            if (StringUtils.isNotBlank(value)) {
                return SqlUtils.ignorecaseLikeString(cb, root.get(field), value);
            }
            return null;
        };
    }

}
