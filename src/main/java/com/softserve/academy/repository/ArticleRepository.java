package com.softserve.academy.repository;

import com.softserve.academy.model.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepository {
    private List<Article> articles = new ArrayList<>();
    private int counter = 0;

    public void create(Article article) {
        if (article != null)
            article.setId(++counter);
            articles.add(article);
    }

    public Article readById(final long id) {
        return articles.stream()
                .filter(a -> a.getId() == id)
                .findAny().orElse(null);
    }

    public void update(Article article) {
        if (article != null) {
            Article oldArticle = articles.stream()
                    .filter(a -> a.getId() == article.getId())
                    .findAny().orElse(null);
            if (oldArticle != null) {
                int index = articles.indexOf(oldArticle);
                articles.set(index, article);
            }
        }
    }

    public void delete(long id) {
        articles.stream()
                .filter(a -> a.getId() == id).findAny()
                .ifPresent(a -> articles.remove(a));
    }

    public List<Article> readAll() {
        return articles;
    }
}
