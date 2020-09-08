package com.babyplug.challenge.user.utils;

import com.babyplug.challenge.user.bean.EUserStatus;
import com.babyplug.challenge.user.domain.User;
import com.babyplug.challenge.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class UserSpecUtils {
    private static Logger logger = LogManager.getLogger(UserSpecUtils.class);

    public static Specification<User> equalLong(String field, Long value) {
        return (root, query, cb) -> {
            if (value != null) {
                return cb.equal(root.<Long>get(field), value);
            }
            return null;
        };
    }

    public static Specification<User> equalBoolean(String field, Boolean value) {
        return (Specification<User>) (root, query, cb) -> {
            if (value != null) {
                return cb.equal(root.<Boolean>get(field), value);
            }
            return null;
        };
    }

    public static Specification<User> likeStringIgnoreCase(String field, String value) {
        return (Specification<User>) (root, query, cb) -> {
            if (StringUtils.isNotBlank(value)) {
//                    return cb.equal(root.<Boolean>get(field), value);
                return SqlUtils.ignorecaseLikeString(cb, root.<String>get(field), value);
            }
            return null;
        };
    }

//    public static Specification<User> greaterThanOrEqualToDate(String field, String value) {
//        return (Specification<User>) (root, query, cb) -> {
//            if (StringUtils.isNotBlank(value)) {
//                try {
//                    Date date = DateUtils.convertStringToDate(value, DateUtils.DD_MM_YYYY);
//                    return cb.greaterThanOrEqualTo(root.<Date>get(field), date);
//                } catch (InvalidFormatException e) {
//                    logger.error(e);
//                }
//            }
//            return null;
//        };
//    }
//
//    public static Specification<User> lessThanOrEqualToDate(String field, String value) {
//        return (Specification<User>) (root, query, cb) -> {
//            if (StringUtils.isNotBlank(value)) {
//                try {
//                    Date date = DateUtils.convertStringToDate(value, DateUtils.DD_MM_YYYY);
//                    return cb.lessThanOrEqualTo(root.<Date>get(field), date);
//                } catch (InvalidFormatException e) {
//                    logger.error(e);
//                }
//            }
//            return null;
//        };
//    }

    public static Specification<User> likeString(String field, String value) {
        return (Specification<User>) (root, query, cb) -> {
            if (StringUtils.isNotBlank(value)) {
                return cb.equal(root.<String>get(field), "%" + value.trim() + "%");
            }
            return null;
        };
    }

//    public static Specification<User> greaterThanOrEqualToLong(String field, Long value) {
//        return (Specification<User>) (root, query, cb) -> {
//            if (value != null) {
//                return cb.greaterThanOrEqualTo(root.<Long>get(field), value);
//            }
//            return null;
//        };
//    }

//    public static Specification<User> lessThanOrEqualToLong(String field, Long value) {
//        return (Specification<User>) (root, query, cb) -> {
//            if (value != null) {
//                return cb.lessThanOrEqualTo(root.<Long>get(field), value);
//            }
//            return null;
//        };
//    }

    public static Specification<User> equalStatus(EUserStatus value) {
        return (Specification<User>) (root, query, cb) -> {
            if (value != null) {
                return cb.equal(root.<EUserStatus>get("status"), value);
            }
            return null;
        };
    }

    public static Specification<User> notEqualLong(String field, Long value) {
        return (root, query, cb) -> {
            if (value != null) {
                return cb.notEqual(root.get(field), value);
            }
            return null;
        };
    }

//    public static Specification<User> longIn(String field, List<Long> values) {
//        return (root, query, cb) -> {
//            if (values != null && !values.isEmpty()) {
//                return root.get(field).in(values);
//            }
//            return null;
//        };
//    }
}
