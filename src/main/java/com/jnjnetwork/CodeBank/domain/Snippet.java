package com.jnjnetwork.CodeBank.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    @ColumnDefault(value = "0")
    private Long likes;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private String language;
    @Column(nullable = false)
    private String code;
    private String img;
    private Boolean isPublic;
}
