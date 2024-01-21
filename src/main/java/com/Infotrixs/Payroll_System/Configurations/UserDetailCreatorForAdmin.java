package com.Infotrixs.Payroll_System.Configurations;

import com.Infotrixs.Payroll_System.Models.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailCreatorForAdmin implements UserDetails {
    String username;

    String password;

    List<GrantedAuthority> authorities;
    public UserDetailCreatorForAdmin(Admin admin) {
        this.username = admin.getUsername();
        this.password = admin.getPassword();

        this.authorities = new ArrayList<>();

        String[] roles = admin.getRole().split(",");
        for(String role: roles){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
            authorities.add(simpleGrantedAuthority);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
