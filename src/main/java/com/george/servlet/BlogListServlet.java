package com.george.servlet;

@WebServlet("/index") // 使用注解配置Servlet路径，无需web.xml
public class BlogListServlet extends HttpServlet {
    private BlogPostDao dao = new BlogPostDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<BlogPost> posts = dao.getAllPosts();
            request.setAttribute("posts", posts); // 将数据放入请求域
            request.getRequestDispatcher("/index.jsp").forward(request, response); // 转发给JSP页面渲染
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}