package Controller;

import Service.Service;
import Service.ServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "GetPlayLists", urlPatterns = {"/GetPlayLists"})
public class GetPlayLists extends HttpServlet {
    ServiceInterface serviceInstance = new Service();


    public void getPlayLists(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceInstance.getPlayLists(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getPlayLists(req, resp);
    }
}
