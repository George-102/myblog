<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.george.model.BlogPost" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>博客详情</title>
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
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .back-link {
            display: inline-block;
            margin-bottom: 20px;
            color: #3498db;
            text-decoration: none;
            font-weight: bold;
        }
        .back-link:hover {
            color: #2980b9;
        }
        .post-header {
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #eee;
        }
        .post-title {
            color: #2c3e50;
            margin-bottom: 15px;
            font-size: 2.2em;
        }
        .post-meta {
            color: #7f8c8d;
            font-size: 0.9em;
        }
        .post-content {
            font-size: 1.1em;
            line-height: 1.8;
            color: #2c3e50;
        }
        .post-content p {
            margin-bottom: 20px;
        }
        .not-found {
            text-align: center;
            padding: 60px 20px;
            color: #999;
        }
        .not-found h2 {
            margin-bottom: 20px;
            color: #e74c3c;
        }
    </style>
</head>
<body>
    <div class="container">
        <%
            BlogPost post = (BlogPost) request.getAttribute("post");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");

            if (post != null) {
        %>
        <a href="./" class="back-link">← 返回博客列表</a>

        <article>
            <div class="post-header">
                <h1 class="post-title"><%= post.getTitle() %></h1>
                <div class="post-meta">
                    发布时间: <%= post.getCreatedAt().format(formatter) %> |
                    文章ID: <%= post.getId() %>
                </div>
            </div>

            <div class="post-content">
                <%= post.getContent().replace("\n", "<br>") %>
            </div>
        </article>
        <%
            } else {
        %>
        <div class="not-found">
            <h2>文章未找到</h2>
            <p>抱歉，您要查看的文章不存在或已被删除。</p>
            <a href="./" class="back-link">返回博客列表</a>
        </div>
        <%
            }
        %>
    </div>
</body>
</html>