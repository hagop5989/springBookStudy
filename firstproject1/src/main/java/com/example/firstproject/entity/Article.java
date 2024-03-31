package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity // JPA 애노테이션 - class 기반 db에 table 생성
@Getter
public class Article {
    @Id // Entity 대표 값
    @GeneratedValue // 자동 생성 기능 추가(자동 채번)
    private Long id;
    @Column // title 필드 선언 (열)
    private String title;
    @Column // content 필드 선언 (열)
    private String content;
}
