package com.tweet.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    //valid email
    private String email;
    @Column(nullable = false, unique = true, length = 100)
    //alphanmeric character
    private String userName;

    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column(nullable = false)
    @JsonIgnore
    private String conPassword;
    @Column(nullable = false)
    private String coontactNum;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts=new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user",referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name = "role",referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    public void setfirstName(Object getfirstName) {
    }

    public void setuserName(Object getuserName) {
    }

    public void setlastName(Object getlastName) {
    }

    public void setemail(Object getemail) {
    }

    public void setpassword(Object getpassword) {
    }

    public void setconpassword(Object getconpassword) {
    }

    public void setcontactNum(Object getcontactNum) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       List<SimpleGrantedAuthority> authorities= this.roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
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