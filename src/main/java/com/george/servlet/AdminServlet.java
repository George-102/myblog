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
        // 关键：设置请求和响应的字符编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String password = request.getParameter("password");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        System.out.println("接收到的标题: " + title);
        System.out.println("接收到的内容: " + content);

        if (ADMIN_PASSWORD.equals(password)) {
            BlogPost post = new BlogPost(title, content);
            if (dao.addPost(post)) {
                response.sendRedirect(request.getContextPath() + "/?message=success");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin?error="+ URLEncoder.encode("发布失败", "UTF-8"));
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin?error="+ URLEncoder.encode("密码错误", "UTF-8"));
        }
    }
}