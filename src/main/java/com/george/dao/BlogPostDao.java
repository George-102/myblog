package com.george.dao;

import com.george.model.BlogPost;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogPostDao {

    /**
     * 获取所有博客文章列表
     * @return 文章列表，按创建时间倒序排列
     */
    public List<BlogPost> getAllPosts() {
        List<BlogPost> posts = new ArrayList<>();
        String sql = "SELECT id, title, content, created_at FROM posts ORDER BY created_at DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                BlogPost post = new BlogPost();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                posts.add(post);
            }
        } catch (SQLException e) {
            System.err.println("获取博客列表失败: " + e.getMessage());
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * 根据ID获取单篇博客文章
     * @param id 文章ID
     * @return 博客文章对象，如果不存在返回null
     */
    public BlogPost getPostById(int id) {
        String sql = "SELECT id, title, content, created_at FROM posts WHERE id = ?";
        BlogPost post = null;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    post = new BlogPost();
                    post.setId(rs.getInt("id"));
                    post.setTitle(rs.getString("title"));
                    post.setContent(rs.getString("content"));
                    post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            System.err.println("根据ID获取文章失败: " + e.getMessage());
            e.printStackTrace();
        }
        return post;
    }

    /**
     * 添加新的博客文章
     * @param post 包含标题和内容的博客文章对象
     * @return 添加成功返回true，失败返回false
     */
    public boolean addPost(BlogPost post) {
        String sql = "INSERT INTO posts (title, content) VALUES (?, ?)";
        System.out.println("执行SQL: " + sql);
        System.out.println("参数 - 标题: " + post.getTitle());
        System.out.println("参数 - 内容长度: " + post.getContent().length());

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());

            int affectedRows = pstmt.executeUpdate();
            System.out.println("受影响行数: " + affectedRows);

            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("添加文章失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新现有的博客文章
     * @param post 包含ID、标题和内容的博客文章对象
     * @return 更新成功返回true，失败返回false
     */
    public boolean updatePost(BlogPost post) {
        String sql = "UPDATE posts SET title = ?, content = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            pstmt.setInt(3, post.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("更新文章失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据ID删除博客文章
     * @param id 要删除的文章ID
     * @return 删除成功返回true，失败返回false
     */
    public boolean deletePost(int id) {
        String sql = "DELETE FROM posts WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("删除文章失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取最近的文章（用于首页展示等）
     * @param limit 获取的文章数量
     * @return 最近的博客文章列表
     */
    public List<BlogPost> getRecentPosts(int limit) {
        List<BlogPost> posts = new ArrayList<>();
        String sql = "SELECT id, title, content, created_at FROM posts ORDER BY created_at DESC LIMIT ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BlogPost post = new BlogPost();
                    post.setId(rs.getInt("id"));
                    post.setTitle(rs.getString("title"));
                    post.setContent(rs.getString("content"));
                    post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            System.err.println("获取最近文章失败: " + e.getMessage());
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * 根据关键词搜索文章
     * @param keyword 搜索关键词
     * @return 匹配的文章列表
     */
    public List<BlogPost> searchPosts(String keyword) {
        List<BlogPost> posts = new ArrayList<>();
        String sql = "SELECT id, title, content, created_at FROM posts WHERE title LIKE ? OR content LIKE ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BlogPost post = new BlogPost();
                    post.setId(rs.getInt("id"));
                    post.setTitle(rs.getString("title"));
                    post.setContent(rs.getString("content"));
                    post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            System.err.println("搜索文章失败: " + e.getMessage());
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * 获取文章总数
     * @return 文章总数
     */
    public int getTotalPosts() {
        String sql = "SELECT COUNT(*) as total FROM posts";
        int total = 0;

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("获取文章总数失败: " + e.getMessage());
            e.printStackTrace();
        }
        return total;
    }
}