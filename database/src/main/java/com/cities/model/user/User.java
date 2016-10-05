package com.cities.model.user;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
@Table(name="user")
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy= IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;
    private String name;
    private String password;
    private String country;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({SAVE_UPDATE})
    @JoinTable(name = "user_userRole", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "userrole_id",
                    nullable = false, updatable = false) })
    private Set<UserRole> userRoles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return "id="+id+", name="+name+", country="+country;
    }
}
