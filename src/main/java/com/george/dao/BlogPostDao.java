package com.george.dao;

public class BlogPostDao {
    public List<BlogPost> getAllPosts() {
        List<BlogPost> posts = new ArrayList<>();
        // 使用JDBC查询数据库 `SELECT id, title, content, created_at FROM posts ORDER BY created_at DESC`
        // 将ResultSet结果封装到BlogPost对象中，并添加到posts列表
        return posts;
    }

    public void addPost(BlogPost post) {
        // 使用JDBC执行插入 `INSERT INTO posts (title, content) VALUES (?, ?)`
    }
}