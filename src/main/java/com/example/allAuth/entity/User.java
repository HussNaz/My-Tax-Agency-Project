package com.example.allAuth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"),indexes = {@Index(name = "idx_user_email", columnList = "email")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotEmpty(message = "User Name Should not be empty.")
    @Column(name = "user_name", unique = true, nullable = false)
    @Email
    private String username;

    @NotEmpty(message = "Email Should not be empty.")
    @Email(message = "Please enter a valid email address")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotEmpty(message = "Phone Number Should not be empty.")
    @Size(min = 11, message = "Phone number should have at least 11 characters")
    private String phoneNumber;


    @NotEmpty(message = "Password Should not be empty.")
    @Size(min = 6, message = "Password should have at least 6 characters")
    @JsonIgnore
    private String password;

    // Corrected @ManyToMany relation with Role
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<Role> roles= new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private Taxpayer taxpayer;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    // New fields for password reset
    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "token_expiration_date")
    private LocalDateTime tokenExpirationDate;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedAt = LocalDateTime.now();
    }


    public User(String username, String email, String password, String mobileNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = mobileNumber;
    }
}
