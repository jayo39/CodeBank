package com.jnjnetwork.CodeBank.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Snippet extends CreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private String language;
    @Column(nullable = false)
    private String code;
    private String img;
    private Boolean isPublic;

    @OneToMany
    @JoinColumn(name = "snippet_id")
    @ToString.Exclude
    private List<Upvote> upvotes = new ArrayList<>();
}
