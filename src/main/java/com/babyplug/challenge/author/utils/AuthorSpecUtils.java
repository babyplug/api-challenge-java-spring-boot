package com.babyplug.challenge.author.utils;

import com.babyplug.challenge.author.domain.Author;
import com.babyplug.challenge.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class AuthorSpecUtils {

    public static Specification<Author> equalBoolean(String field, Boolean value) {
        return (Specification<Author>) (root, criteriaQuery, cb) -> {
            if (value != null) {
                return cb.equal(root.<Boolean>get(field), value);
            }
            return null;
        };
    }

    public static Specification<Author> likeString(String field, String value) {
        return (Specification<Author>) (root, criteriaQuery, cb) -> {
            if (StringUtils.isNotBlank(value)) {
                return SqlUtils.ignorecaseLikeString(cb, root.get(field), value);
            }
            return null;
        };
    }

}
