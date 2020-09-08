package com.babyplug.challenge.utils;

import com.babyplug.challenge.user.bean.EUserStatus;
import com.babyplug.challenge.user.domain.User;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserUtils {

//    public static boolean checkUserType(User user, String expectType) {
//        if (user != null && user.getMasterUserType() != null) {
//            if (user.getMasterUserType().getName().equalsIgnoreCase(expectType)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static void validateUserStatus(User user) {
        if ( (user.getDeleted() != null && user.getDeleted()) || (user.getStatus() != null && user.getStatus() == EUserStatus.INACTIVE) ) {
            throw new UsernameNotFoundException("This user is inactive !");
        }
    }

}
