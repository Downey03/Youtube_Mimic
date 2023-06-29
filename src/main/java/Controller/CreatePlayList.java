package Controller;

import Service.Service;
import Service.ServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "/CreatePlayList",urlPatterns ={"/CreatePlayList"} )
public class CreatePlayList extends HttpServlet {
    ServiceInterface serviceInstance = new Service();

    public void createPlayList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceInstance.createPlayList(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        createPlayList(req, resp);
    }
}
