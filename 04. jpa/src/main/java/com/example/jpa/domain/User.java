package com.example.jpa.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String name;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference
    private List<News> news;
}

enum RoleType {
    ADMIN, USER
}