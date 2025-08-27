<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.george.model.BlogPost" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>我的个人博客</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            color: #333;
            background-color: #f4f4f4;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        header {
            text-align: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #eee;
        }
        h1 {
            color: #2c3e50;
            margin-bottom: 10px;
        }
        .post {
            margin-bottom: 25px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background: #fff;
            transition: transform 0.2s;
        }
        .post:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .post h2 {
            color: #3498db;
            margin-bottom: 10px;
        }
        .post h2 a {
            text-decoration: none;
            color: inherit;
        }
        .post h2 a:hover {
            color: #2980b9;
        }
        .post-content {
            color: #666;
            margin-bottom: 15px;
            line-height: 1.6;
        }
        .post-meta {
            color: #999;
            font-size: 0.9em;
        }
        .empty-state {
            text-align: center;
            padding: 40px;
            color: #999;
        }
        .admin-link {
            display: block;
            text-align: center;
            margin-top: 30px;
            padding: 15px;
            background: #ecf0f1;
            border-radius: 5px;
            text-decoration: none;
            color: #7f8c8d;
            font-weight: bold;
        }
        .admin-link:hover {
            background: #bdc3c7;
            color: #2c3e50;
        }
        footer {
            text-align: center;
            margin-top: 40px;
            color: #999;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1>📝 我的个人博客</h1>
            <p>分享技术与生活</p>
        </header>

        <%
            List<BlogPost> posts = (List<BlogPost>) request.getAttribute("posts");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");

            if (posts != null && !posts.isEmpty()) {
                for (BlogPost post : posts) {
                    String preview = post.getContent().length() > 150 ?
                        post.getContent().substring(0, 150) + "..." : post.getContent();
        %>
        <article class="post">
            <h2>
                <a href="post?id=<%= post.getId() %>"><%= post.getTitle() %></a>
            </h2>
            <div class="post-content">
                <%= preview %>
            </div>
            <div class="post-meta">
                发布时间: <%= post.getCreatedAt().format(formatter) %> |
                文章ID: <%= post.getId() %>
            </div>
        </article>
        <%
                }
            } else {
        %>
        <div class="empty-state">
            <h2>暂无内容</h2>
            <p>还没有发布任何博客文章，快去后台发布第一篇吧！</p>
        </div>
        <%
            }
        %>

        <a href="admin" class="admin-link">🔐 进入后台管理</a>

        <footer>
            <p>Powered by Java Servlet & MySQL | © 2025 我的博客</p>
        </footer>
    </div>
</body>
</html>