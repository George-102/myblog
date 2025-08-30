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
            System.out.println("密码参数: " + password);
            System.out.println("标题参数: " + title);
            System.out.println("内容长度: " + (content != null ? content.length() : "null"));
            System.out.println("管理员密码验证: " + ADMIN_PASSWORD.equals(password));

            if (ADMIN_PASSWORD.equals(password)) {
                BlogPost post = new BlogPost(title, content);
                System.out.println("创建 BlogPost 对象成功");

                boolean addResult = dao.addPost(post);
                System.out.println("数据库插入结果: " + addResult);

                if (addResult) {
                    System.out.println("发布成功，准备重定向到首页");
                    response.sendRedirect(request.getContextPath() + "/?message="+
                            URLEncoder.encode("发布成功", "UTF-8"));
                    return;
                } else {
                    System.out.println("发布失败，重定向到错误页面");
                    response.sendRedirect(request.getContextPath() + "/admin?error="+
                            URLEncoder.encode("发布失败，请检查数据库", "UTF-8"));
                    return;
                }
            } else {
                System.out.println("密码错误，重定向到错误页面");
                response.sendRedirect(request.getContextPath() + "/admin?error="+
                        URLEncoder.encode("密码错误", "UTF-8"));
                return;
            }
        } catch (Exception e) {
            System.err.println("发布文章时发生异常: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin?error="+
                    URLEncoder.encode("系统错误: " + e.getMessage(), "UTF-8"));
        }
    }
}