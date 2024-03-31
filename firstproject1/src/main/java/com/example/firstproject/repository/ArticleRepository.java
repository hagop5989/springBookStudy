package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article,Long> {
    // Article- 엔티티의 클래스타입, Long- 엔티티 대표값 타입(id)

    @Override
    ArrayList<Article> findAll();
}
