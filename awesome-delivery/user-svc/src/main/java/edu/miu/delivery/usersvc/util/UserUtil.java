package edu.miu.delivery.usersvc.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import edu.miu.delivery.usersvc.authentication.UserDetail;
import edu.miu.delivery.usersvc.model.User;

public class UserUtil {

    public static UserDetail userToPrincipal(User user) {
        UserDetail userDetail = new UserDetail();
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());

        userDetail.setUsername(user.getUsername());
        userDetail.setPassword(user.getPassword());
        userDetail.setAuthorities(authorities);
        userDetail.setUserId(user.getId());
        return userDetail;
    }

    public static UserDetail create(User user) {
        return new UserDetail();
    }

}
