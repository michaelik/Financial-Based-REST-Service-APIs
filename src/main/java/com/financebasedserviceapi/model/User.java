package com.financebasedserviceapi.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    Long userId;

    @Column(name = "user_name", columnDefinition = "VARCHAR(255) NOT NULL")
    String userName;

    @Column(name = "first_name", columnDefinition = "VARCHAR(255) NOT NULL")
    String firstName;

    @Column(name = "last_name", columnDefinition = "VARCHAR(255) NOT NULL")
    String lastName;

    @Column(name = "email", columnDefinition = "VARCHAR(255) NOT NULL")
    String email;

    @Column(name = "password", columnDefinition = "VARCHAR(255) NOT NULL")
    String password;

    @OneToOne(targetEntity = Account.class, cascade = CascadeType.ALL)
    Account account;

    @OneToMany(targetEntity = Transaction.class, cascade = CascadeType.ALL)
    List<Transaction> transaction;

    @Builder.Default
    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @UpdateTimestamp
    @Column(name="updated_at")
    LocalDateTime updatedAt = LocalDateTime.now();
}
