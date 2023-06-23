package Filters;


import Utilities.JwtUtil;
import Utilities.Utils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtFilter implements CorsFilter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String jwtToken = Utils.getUserId(req);
        System.out.println(req.getHeader("Authorization"));
//        System.out.println(jwtToken+" \n"+JwtUtil.verifyToken(jwtToken));
        System.out.println("received");
        try{

            if(JwtUtil.verifyToken(jwtToken))
                chain.doFilter(req,resp);
            else resp.sendRedirect(req.getContextPath()+"/page.html");
        }catch (Exception e){
            resp.sendRedirect(req.getContextPath()+"/page.html");
        }

    }

    @Override
    public void destroy() {

    }
}
