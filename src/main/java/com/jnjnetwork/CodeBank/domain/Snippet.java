package com.jnjnetwork.CodeBank.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private Long likeCount = 0L;
    @OneToMany
    @JoinColumn(name = "snippet_id")
    @ToString.Exclude
    private List<Upvote> upvotes = new ArrayList<>();
    @ManyToOne
    @ToString.Exclude
    private User user;
}
