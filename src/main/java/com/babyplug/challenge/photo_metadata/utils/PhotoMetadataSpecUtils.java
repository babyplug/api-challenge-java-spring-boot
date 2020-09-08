package com.babyplug.challenge.photo_metadata.utils;

import com.babyplug.challenge.photo_metadata.domain.PhotoMetadata;
import com.babyplug.challenge.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class PhotoMetadataSpecUtils {

    public static Specification<PhotoMetadata> equalBoolean(String field, Boolean value) {
        return (root, criteriaQuery, cb) -> {
            if (value != null) {
                return cb.equal(root.<Boolean>get(field), value);
            }
            return null;
        };
    }

    public static Specification<PhotoMetadata> likeString(String field, String value) {
        return (root, criteriaQuery, cb) -> {
            if (StringUtils.isNotBlank(value)) {
                return SqlUtils.ignorecaseLikeString(cb, root.get(field), value);
            }
            return null;
        };
    }

}
