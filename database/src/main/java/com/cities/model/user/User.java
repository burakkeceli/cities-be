package com.cities.model.user;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
@Table(name="user")
@Data
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy= IDENTITY)
    private Integer id;

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

    @Override
    public String toString(){
        return "id="+id+", name="+name+", country="+country;
    }
}
