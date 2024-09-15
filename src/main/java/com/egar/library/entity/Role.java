package com.egar.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private RoleType authority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    public GrantedAuthority authority(){
        return new SimpleGrantedAuthority(authority.name());
    }

    public static Role from(RoleType role){
        Role newRole = new Role();
        newRole.setAuthority(role);
        return newRole;
    }
}
