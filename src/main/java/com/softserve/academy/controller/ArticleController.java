package com.softserve.academy.controller;

import com.softserve.academy.exception.NotFoundException;
import com.softserve.academy.model.Article;
import com.softserve.academy.security.User;
import com.softserve.academy.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.List;

@RestController
public class ArticleController {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/articles")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void create(@RequestBody Article article) {
        article.setCreatedAt(new Date(System.currentTimeMillis()));
        article.setAuthorId(getAuthorId());
        articleRepository.create(article);
    }

    @GetMapping("/articles/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Article readById(@PathVariable long id) {
        Article article = articleRepository.readById(id);
        if (article != null) {
            return article;
        }
        throw new NotFoundException("Article not Found!");
    }

    @PutMapping("/articles")
    @ResponseStatus(code = HttpStatus.OK)
    public void update(@RequestBody Article article) {
        articleRepository.update(article);
    }

    @DeleteMapping("/articles/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable long id) {
        articleRepository.delete(id);
    }

    @GetMapping("/articles")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Article> readAll() {
        return articleRepository.readAll();
    }

    private long getAuthorId() {
        return ((User)SecurityContextHolder.getContext().getAuthentication().getDetails()).getId();
    }
}
