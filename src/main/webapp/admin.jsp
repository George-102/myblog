<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = request.getParameter("error");
    String message = request.getParameter("message");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>后台管理 - 发布新文章</title>
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
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .admin-container {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            width: 100%;
            max-width: 600px;
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
        h1 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 30px;
            font-size: 2em;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #2c3e50;
        }
        input[type="text"],
        input[type="password"],
        textarea {
            width: 100%;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 6px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        input[type="text"]:focus,
        input[type="password"]:focus,
        textarea:focus {
            outline: none;
            border-color: #3498db;
        }
        textarea {
            min-height: 200px;
            resize: vertical;
        }
        .btn {
            background: #3498db;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            width: 100%;
            transition: background 0.3s;
        }
        .btn:hover {
            background: #2980b9;
        }
        .message {
            padding: 15px;
            border-radius: 6px;
            margin-bottom: 20px;
            text-align: center;
        }
        .error {
            background: #ffecec;
            color: #e74c3c;
            border: 1px solid #f5c6cb;
        }
        .success {
            background: #e8f5e9;
            color: #2ecc71;
            border: 1px solid #c3e6cb;
        }
        .password-note {
            font-size: 0.9em;
            color: #7f8c8d;
            margin-top: 5px;
        }
    </style>
</head>
<body>
    <div class="admin-container">
        <a href="./" class="back-link">← 返回博客首页</a>

        <h1>📝 发布新文章</h1>

        <% if (error != null) { %>
        <div class="message error">
            ❌ <%= error %>
        </div>
        <% } %>

        <% if (message != null) { %>
        <div class="message success">
            ✅ <%= message %>
        </div>
        <% } %>

        <form action="admin" method="post">
            <div class="form-group">
                <label for="password">管理员密码:</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <label for="title">文章标题:</label>
                <input type="text" id="title" name="title" required placeholder="请输入文章标题">
            </div>

            <div class="form-group">
                <label for="content">文章内容:</label>
                <textarea id="content" name="content" required placeholder="请输入文章内容..."></textarea>
            </div>

            <button type="submit" class="btn">发布文章</button>
        </form>
    </div>
</body>
</html>