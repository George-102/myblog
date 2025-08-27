package com.george.servlet;

import com.george.dao.BlogPostDao;
import com.george.model.BlogPost;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/post")
public class BlogPostServlet extends HttpServlet {
    private BlogPostDao dao = new BlogPostDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                int id = Integer.parseInt(idStr);
                BlogPost post = dao.getPostById(id);
                if (post != null) {
                    request.setAttribute("post", post);
                    request.getRequestDispatcher("/post.jsp").forward(request, response);
                    return;
                }
            }
            response.sendRedirect(request.getContextPath() + "/");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}