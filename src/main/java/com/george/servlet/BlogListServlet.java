package com.george.servlet;

import com.george.dao.BlogPostDao;
import com.george.model.BlogPost;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("")
public class BlogListServlet extends HttpServlet {
    private BlogPostDao dao = new BlogPostDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            System.out.println("开始获取博客列表...");
            List<BlogPost> posts = dao.getAllPosts();
            System.out.println("获取到 " + posts.size() + " 篇文章");

            request.setAttribute("posts", posts);
            request.getRequestDispatcher("/index.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("加载博客列表失败: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "加载博客列表失败: " + e.getMessage());
        }
    }
}