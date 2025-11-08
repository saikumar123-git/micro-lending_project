package com.example.micro_lending.Project.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fullName;


    @Email
    @Column(nullable = false, unique = true)
    private String email;


    @NotBlank
    @Column(nullable = false)
    private String phone;


    @Column(nullable = false)
    private String passwordHash;


    private boolean enabled = true;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();






    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<LoanRequest> loanRequests = new ArrayList<>();


    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<DocumentUpload> documents = new ArrayList<>();


    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}

