package Filters;


import Utilities.JwtUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        System.out.println("received");
        try{
            JwtUtil.verifyToken(req.getParameter("jwtToken"));
            chain.doFilter(req,resp);
        }catch (Exception e){
            resp.sendRedirect(req.getContextPath()+"/welcome.html");
        }

    }

    @Override
    public void destroy() {

    }
}
