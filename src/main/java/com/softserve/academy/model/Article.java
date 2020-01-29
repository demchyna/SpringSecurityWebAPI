package com.softserve.academy.model;

import java.sql.Date;

public class Article {
    private long id;
    private String name;
    private String content;
    private Date createdAt;
    private long authorId;

    public Article() {    }

    public Article(String name, String content, Date createdAt, int authorId) {
        this.name = name;
        this.content = content;
        this.createdAt = createdAt;
        this.authorId = authorId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Article { " +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", content = '" + content + '\'' +
                ", createdAt = " + createdAt +
                ", authorId = " + authorId +
                " }";
    }
}
