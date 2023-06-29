package Controller;

import Service.Service;
import Service.ServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "DeletePlayList",urlPatterns = {"/DeletePlayList"})
public class DeletePlayList extends HttpServlet {
    ServiceInterface serviceInstance = new Service();

    public void deletePlayList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceInstance.deletePlayList(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        deletePlayList(req, resp);
    }
}
