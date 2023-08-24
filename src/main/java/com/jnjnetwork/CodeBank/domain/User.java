package com.jnjnetwork.CodeBank.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User extends CreatedTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String name;
    @Column(nullable = false)
    private String password;
    @Transient
    private String re_password;
    private String p_img;
    private String job;

    @OneToMany
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<Snippet> snippets = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<Upvote> upvotes = new ArrayList<>();

    // User:Role = N:M
    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @Builder.Default
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();
    public void addRole(Role... roles) {
        if(roles != null) {
            Collections.addAll(this.roles, roles);
        }
    }

    @ManyToMany
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followee_id")
    )
    private List<User> following = new ArrayList<>();

    @ManyToMany(mappedBy = "following")
    private List<User> followers = new ArrayList<>();

}
