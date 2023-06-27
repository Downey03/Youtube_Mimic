package Controller;

import Service.ServiceImple;
import Service.ServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "AddItemToPlayList",urlPatterns = {"/AddItemToPlayList"})
public class AddItemToPlayList extends HttpServlet {
    ServiceInterface serviceInstance = new ServiceImple();

    public void addItemToPlayList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceInstance.addItemToPlayList(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addItemToPlayList(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addItemToPlayList(req, resp);
    }
}
