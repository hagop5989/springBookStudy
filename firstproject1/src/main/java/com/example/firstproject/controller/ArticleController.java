package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j // Simple Logging Facade for Java // 로깅기능 애노테이션
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());
//        System.out.println(form.toString());
        // 1. DTO 를 Entity 로 변환
        Article article = form.toEntity();
        log.info(article.toString());
//        System.out.println(article.toString());
        // 2. Repository 로 Entity 를 DB 에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
//        System.out.println(saved.toString());

        return "redirect:/articles/" + saved.getId() ;
    }

    @GetMapping("/articles/{id}") // {}중괄호 안의 문자는 변수로 사용 됨
    public String show(@PathVariable Long id, Model model){
        // @PathVariable - url 요청으로 들어온 전달값을 매개변수로 가져옴
        log.info("id = " + id);
        // 1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // .orElse() -> 값이 없으면 ()안의 값을 반환함

        // 2. 모델에 데이터 등록하기
        model.addAttribute("article",articleEntity);
        // 3. 뷰 페이지 반환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll(); // 데이터 묶음을 List 에 묶인 Article 타입으로 가져옴
        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList",articleEntityList);
        // 3. 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 뷰 페이지 설정하기
        model.addAttribute("article",articleEntity);
        // 뷰페이지에 articleEntity 이란 객체를 article 이란 name 으로 등록하여 사용케 함
        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleForm form) { // DTO 는 메서드의 매개변수로 받아온다
        log.info(form.toString());
        // 1. DTO -> Entity 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        // 2. Entity -> DB 저장
        // 2-1. DB 에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        if (target != null) {
           articleRepository.save(articleEntity); // 엔티티를 DB 에 저장(갱신)
        }
        // 3. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!!!");
        // 1. 삭제할 대상 가져오기
        // 2. 대상 엔티티 삭제하기
        // 3. 결과 페이지로 리다이렉트하기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제됐습니다!!");
        }
        return "redirect:/articles";
    }
}
