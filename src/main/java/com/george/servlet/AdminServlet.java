package com.george.servlet;

import com.george.dao.BlogPostDao;
import com.george.model.BlogPost;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private BlogPostDao dao = new BlogPostDao();
    private static final String ADMIN_PASSWORD = "ady190719";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置响应编码
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            String password = request.getParameter("password");
            String title = request.getParameter("title");
            String content = request.getParameter("content");

            System.out.println("=== 发布文章请求开始 ===");
            System.out.println("Context Path: " + request.getContextPath());
            System.out.println("Request URL: " + request.getRequestURL());

            if (ADMIN_PASSWORD.equals(password)) {
                BlogPost post = new BlogPost(title, content);

                if (dao.addPost(post)) {
                    String redirectUrl = request.getContextPath() + "/?message=" +
                            URLEncoder.encode("发布成功", "UTF-8");
                    System.out.println("重定向URL: " + redirectUrl);

                    // 测试性输出到响应，帮助调试
                    response.getWriter().println("Debug: Redirecting to: " + redirectUrl);
                    response.getWriter().flush();

                    response.sendRedirect(redirectUrl);
                    return;
                } else {
                    String errorUrl = request.getContextPath() + "/admin?error=" +
                            URLEncoder.encode("发布失败", "UTF-8");
                    System.out.println("错误重定向URL: " + errorUrl);
                    response.sendRedirect(errorUrl);
                    return;
                }
            } else {
                String errorUrl = request.getContextPath() + "/admin?error=" +
                        URLEncoder.encode("密码错误", "UTF-8");
                System.out.println("密码错误重定向URL: " + errorUrl);
                response.sendRedirect(errorUrl);
                return;
            }
        } catch (Exception e) {
            System.err.println("发布文章时发生异常: " + e.getMessage());
            e.printStackTrace();
            // 临时返回错误信息到页面以便调试
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}