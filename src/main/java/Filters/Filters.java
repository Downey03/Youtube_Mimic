package Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")

public class Filters implements javax.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setHeader("Access-Control-Allow-Methods","POST, PUT, GET, OPTIONS, DELETEE");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers","X-Requested-with,Origin,Content-Type, Accept, x-device-user-agent, Content-Type");

        if(req.getMethod().equals("OPTIONS")){
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        chain.doFilter(req,resp);


    }

    @Override
    public void destroy() {

    }
}
