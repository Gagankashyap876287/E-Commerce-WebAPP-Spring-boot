package com.sheryians.major.Model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    @Column(nullable = false)
    private String firstname;

    private String lastname;

    @NotEmpty
    @Column(nullable = false,unique = true)
    @Email(message = "{error.invalid_email}")
    private String email;


    private String password;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(
            name="user_role",
            joinColumns = {@JoinColumn(name="USER_ID",referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name="ROLE_ID",referencedColumnName = "ID")}
    )
    private List<Role> roles;

    public User(User user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.password =user.getPassword();
        this.roles = user.getRoles();
    }
    public User(){

    }
}
