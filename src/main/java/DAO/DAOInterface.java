package DAO;

import Bean.PlayList;
import Bean.YTLink;
import DTO.PlayListDTO;
import DTO.UserDTO;

import java.util.ArrayList;
import java.util.List;

public interface DAOInterface {
    PlayListDTO getVideos(String searchKeyword);

    PlayListDTO getVideos();

    String createUser(UserDTO userDTO) throws Exception;

    PlayListDTO createPlayList(PlayListDTO playListDTO) throws Exception;

    String validateLogin(UserDTO userDTO) throws Exception;

    PlayListDTO getPlayLists(PlayListDTO playListDTO);

    PlayListDTO getPlayListItem(PlayListDTO playListDTO);

    PlayListDTO addItemToPlayList(PlayListDTO playListDTO);

    PlayListDTO removeItemFromPlayList(PlayListDTO playListDTO);

    void deletePlayList(PlayListDTO playListDTO);
}
