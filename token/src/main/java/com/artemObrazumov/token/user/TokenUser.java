package com.artemObrazumov.token.user;

import com.artemObrazumov.token.Token;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class TokenUser extends User {

    private final Long userId;
    private final Token token;

    public TokenUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                     Token token, Long userId) {
        super(username, password, authorities);
        this.token = token;
        this.userId = userId;
    }

    public TokenUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
                     Token token, Long userId) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.token = token;
        this.userId = userId;
    }

    public Token getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }
}
