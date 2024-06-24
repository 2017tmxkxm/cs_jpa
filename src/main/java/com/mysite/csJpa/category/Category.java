package com.mysite.csJpa.category;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String categoryName;

    @Builder
    public Category(Integer id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}
