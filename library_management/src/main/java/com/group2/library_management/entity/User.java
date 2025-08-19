package com.group2.library_management.entity;

import java.time.LocalDate;
import java.util.List;

import com.group2.library_management.entity.enums.RoleType;
import com.group2.library_management.entity.enums.UserStatus;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false)
    private RoleType role;

    @Column(nullable = false)
    private UserStatus status; 

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Follow> follows;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<BorrowingReceipt> borrowingReceipts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;
}
