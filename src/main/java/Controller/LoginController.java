package Controller;

import Service.ServiceImple;
import Service.ServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "LoginController",urlPatterns = {"/home/LoginController"})
public class LoginController extends HttpServlet {

    ServiceInterface serviceInstance = new ServiceImple();


    public void validateLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceInstance.validateLogin(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        validateLogin(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        validateLogin(req, resp);
    }
}
