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
    @Column(name="us_id")
    @GeneratedValue(strategy= IDENTITY)
    private Integer id;

    @Column(name="us_user_name", unique = true, nullable = false)
    private String username;

    @Column(name="us_email", unique = true)
    private String email;

    @Column(name="us_first_name")
    private String firstName;

    @Column(name="us_last_name")
    private String lastName;

    @Column(name="us_password", nullable = false)
    private String password;

    @Column(name="us_country")
    private String country;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({SAVE_UPDATE})
    @JoinTable(name = "user_userRole", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "userrole_id",
                    nullable = false, updatable = false) })
    private Set<UserRole> userRoles;
}
