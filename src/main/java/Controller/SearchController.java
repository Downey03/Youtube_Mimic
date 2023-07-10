package Controller;

import Service.Service;
import Service.ServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//@WebServlet(name="SearchController",urlPatterns = {"/SearchController"})
public class SearchController extends HttpServlet {

    ServiceInterface serviceInterface = new Service();


    public void getVideos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceInterface.getVideos(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getVideos(req, resp);
    }

}
