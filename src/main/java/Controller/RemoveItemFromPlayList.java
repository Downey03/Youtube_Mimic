package Controller;

import Service.Service;
import Service.ServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "RemoveItemFromPlayList",urlPatterns = {"/RemoveItemFromPlayList"})
public class RemoveItemFromPlayList extends HttpServlet {

    ServiceInterface serviceInstance = new Service();

    public void removeItemFromPlayList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceInstance.removeItemFromPlayList(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        removeItemFromPlayList(req, resp);
    }
}
