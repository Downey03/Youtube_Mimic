package Controller;

import Service.ServiceImple;
import Service.ServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetPlayListItems",urlPatterns = {"/GetPlayListItems"})
public class GetPlayListItems extends HttpServlet {
    ServiceInterface serviceInstance = new ServiceImple();

    protected void getPlayListItems(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceInstance.getPlayListItems(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getPlayListItems(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getPlayListItems(req, resp);
    }
}
