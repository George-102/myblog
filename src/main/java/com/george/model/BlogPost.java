package com.george.model;

import java.time.LocalDateTime;

public class BlogPost {
    private int id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    // 构造方法
    public BlogPost() {}

    public BlogPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getter 和 Setter 方法
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
