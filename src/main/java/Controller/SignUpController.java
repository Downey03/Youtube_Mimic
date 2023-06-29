package Controller;

import Service.Service;
import Service.ServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "SignUpController", urlPatterns = {"/SignUpController"})
public class SignUpController extends HttpServlet {

    ServiceInterface serviceInstance = new Service();

    public void createUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceInstance.createUser(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        createUser(req, resp);
    }

}
