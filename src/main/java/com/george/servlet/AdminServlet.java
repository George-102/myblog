package com.george.servlet;

import com.george.dao.BlogPostDao;
import com.george.model.BlogPost;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private BlogPostDao dao = new BlogPostDao();
    private static final String ADMIN_PASSWORD = "ady190719";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String password = request.getParameter("password");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        System.out.println("=== 发布文章请求 ===");
        System.out.println("密码: " + (password != null ? "***" : "null"));
        System.out.println("标题: " + title);
        System.out.println("内容长度: " + (content != null ? content.length() : "null"));

        try {
            if (ADMIN_PASSWORD.equals(password)) {
                if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/admin?error=标题和内容不能为空");
                    return;
                }

                BlogPost post = new BlogPost(title.trim(), content.trim());
                if (dao.addPost(post)) {
                    System.out.println("文章发布成功");
                    response.sendRedirect(request.getContextPath() + "/?message=文章发布成功");
                } else {
                    System.err.println("文章发布失败");
                    response.sendRedirect(request.getContextPath() + "/admin?error=发布失败，请检查数据库连接");
                }
            } else {
                System.err.println("密码错误");
                response.sendRedirect(request.getContextPath() + "/admin?error=密码错误");
            }
        } catch (Exception e) {
            System.err.println("发布文章时发生异常: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin?error=服务器错误: " + e.getMessage());
        }
    }
}