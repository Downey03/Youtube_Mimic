package Controller;

import Service.Service;
import Service.ServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//@WebServlet(name = "LoginController",urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    ServiceInterface serviceInstance = new Service();


    public void validateLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceInstance.validateLogin(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        validateLogin(req, resp);
    }
}
