package Service;

import com.google.gson.JsonArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ServiceInterface {
    void getVideos(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void createUser(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void createPlayList(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void deletePlayList(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void getPlayLists(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void validateLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void getPlayListItems(HttpServletRequest req, HttpServletResponse res) throws IOException;

    void addItemToPlayList(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void removeItemFromPlayList(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
