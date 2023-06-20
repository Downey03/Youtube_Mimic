package Service;

import DAO.DAOImple;
import DAO.DAOInterface;
import DTO.PlayListDTO;
import DTO.SearchDTO;
import DTO.UserDTO;
import Utilities.JwtUtil;
import Utilities.Utils;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

public class ServiceImple implements ServiceInterface{

    DAOInterface daoInstance = new DAOImple();

    static Gson gson = new Gson();

    @Override
    public void getVideos(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        String jsonString = Utils.getRequestString(req);
        SearchDTO searchDto = gson.fromJson(new StringReader(jsonString), SearchDTO.class);

        PlayListDTO playListDTO;
        if(searchDto.getSearchKeyword() != null && !searchDto.getSearchKeyword().trim().equals("")) {
            playListDTO = daoInstance.getVideos(searchDto.getSearchKeyword());
        }else{
            playListDTO = daoInstance.getVideos();
        }

        if(playListDTO.ytLinks.size()==0){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);//404
        }else{
            resp.setStatus(HttpServletResponse.SC_FOUND);//302
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(gson.toJson(playListDTO.ytLinks));
        out.flush();

    }

    @Override
    public void createUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String jsonString = Utils.getRequestString(req);
        UserDTO userDTO  = new Gson().fromJson(new StringReader(jsonString), UserDTO.class);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try{
            String userId = daoInstance.createUser(userDTO);
            String jwtToken = JwtUtil.generateToken(userId,userDTO.getUserEmail());
            resp.setStatus(HttpServletResponse.SC_CREATED);//201
            out.print(gson.toJson(jwtToken));
            out.flush();
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);//406
            out.print(gson.toJson(e.getMessage()));
            out.flush();
        }

    }

    @Override
    public void validateLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String reqString = Utils.getRequestString(req);
        UserDTO userDTO = gson.fromJson(new StringReader(reqString), UserDTO.class);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try{
            String userId = daoInstance.validateLogin(userDTO);
            String jwtToken = JwtUtil.generateToken(userId,userDTO.getUserEmail());
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);//202
            out.print(gson.toJson(jwtToken));
            out.flush();
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);//406
            out.print(gson.toJson(e.getMessage()));
            out.flush();
        }
    }

    @Override
    public void createPlayList(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String reqString = Utils.getRequestString(req);
        PlayListDTO playListDTO = gson.fromJson(new StringReader(reqString),PlayListDTO.class);
        playListDTO.userEmail = Utils.getEmail(req);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            playListDTO = daoInstance.createPlayList(playListDTO);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.print(gson.toJson(playListDTO.playLists));
            out.flush();
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            out.print(gson.toJson(e.getMessage()));
            out.flush();
        }
    }

    @Override
    public void deletePlayList(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String reqString = Utils.getRequestString(req);
        PlayListDTO playListDTO = gson.fromJson(new StringReader(reqString),PlayListDTO.class);
        playListDTO.userEmail = Utils.getEmail(req);

        daoInstance.deletePlayList(playListDTO);
        resp.setStatus(HttpServletResponse.SC_OK);

    }

    @Override
    public void getPlayLists(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        PlayListDTO playListDTO = new PlayListDTO();

        playListDTO.userEmail = Utils.getEmail(req);
        playListDTO = daoInstance.getPlayLists(playListDTO);
        resp.setStatus(HttpServletResponse.SC_OK);
        out.print(gson.toJson(playListDTO.playLists));
        out.flush();
    }




    @Override
    public void getPlayListItems(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String reqString = Utils.getRequestString(req);
        PlayListDTO playListDTO = gson.fromJson(new StringReader(reqString), PlayListDTO.class);
        playListDTO.userEmail = Utils.getEmail(req);

        playListDTO = daoInstance.getPlayListItem(playListDTO);
        PrintWriter out = resp.getWriter();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(gson.toJson(playListDTO.ytLinks));
        out.flush();

    }

    @Override
    public void addItemToPlayList(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String reqString = Utils.getRequestString(req);
        PlayListDTO playListDTO = gson.fromJson(new StringReader(reqString), PlayListDTO.class);
        playListDTO.userEmail = Utils.getEmail(req);
        playListDTO = daoInstance.addItemToPlayList(playListDTO);
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(playListDTO.ytLinks));
        out.flush();

    }

    @Override
    public void removeItemFromPlayList(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String reqString = Utils.getRequestString(req);
        PlayListDTO playListDTO = gson.fromJson(new StringReader(reqString), PlayListDTO.class);
        playListDTO.userEmail = Utils.getEmail(req);
        playListDTO = daoInstance.removeItemFromPlayList(playListDTO);
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(playListDTO.ytLinks));
        out.flush();

    }
}
