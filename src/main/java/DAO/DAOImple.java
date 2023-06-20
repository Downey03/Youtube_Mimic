package DAO;

import Bean.PlayList;
import Bean.User;
import Bean.YTLink;
import DTO.PlayListDTO;
import DTO.UserDTO;
import ObjectifyService.ObjectifyInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DAOImple implements DAOInterface{
    @Override
    public PlayListDTO getVideos(String searchKeyword) {

        List<YTLink> ytLinkList = ObjectifyInitializer.ofy().load().type(YTLink.class).list();

        ytLinkList.removeIf(video -> !video.getTitle().toLowerCase().contains(searchKeyword.trim().toLowerCase()));

        PlayListDTO playListDTO = new PlayListDTO();
        playListDTO.ytLinks = ytLinkList;

        return playListDTO;
    }

    @Override
    public PlayListDTO getVideos() {

        List<YTLink> ytLinkList = ObjectifyInitializer.ofy().load().type(YTLink.class).list();

        PlayListDTO playListDTO = new PlayListDTO();
        playListDTO.ytLinks = ytLinkList;

        return playListDTO;

    }

    @Override
    public String createUser(UserDTO userDTO) throws Exception {

        String userEmail = userDTO.getUserEmail();

        User user = ObjectifyInitializer.ofy().load().type(User.class).filter("userEmail",userEmail).first().now();
        if(user != null) throw new Exception("User Already Found");

        user = User.builder()
                .id(UUID.randomUUID().toString())
                .name(userDTO.getName())
                .userEmail(userEmail)
                .password(userDTO.getPassword())
                .playLists(new ArrayList<>())
                .build();

        ObjectifyInitializer.ofy().save().entity(user).now();

        return user.getId();
    }

    @Override
    public String validateLogin(UserDTO userDTO) throws Exception {

        User user = ObjectifyInitializer.ofy().load().type(User.class).filter("userEmail",userDTO.getUserEmail()).first().now();

        if(user == null) throw new Exception("Invalid Email");
        if(!user.getPassword().equals(userDTO.getPassword())) throw new Exception("Invalid Password");

        return user.getId();
    }

    @Override
    public PlayListDTO createPlayList(PlayListDTO playListDTO) throws Exception {

        String userEmail = playListDTO.userEmail;
        String playListName = playListDTO.playListName;

        User user = ObjectifyInitializer.ofy().load().type(User.class).filter("userEmail",userEmail).first().now();

        List<String> playLists = user.getPlayLists();
        playListName = playListName.trim();

        if(playLists == null) playLists = new ArrayList<>();
        if(playLists.contains(playListName)) throw new Exception("PlayList Already Found");

        playLists.add(playListName);
        user.setPlayLists(playLists);

        PlayList playList = PlayList.builder()
                .id(playListName+userEmail)
                .playListName(playListName)
                .userEmail(user.getUserEmail())
                .videoList(new ArrayList<>())
                .build();

        ObjectifyInitializer.ofy().save().entity(playList);
        ObjectifyInitializer.ofy().save().entity(user);

        playListDTO.playLists = playLists;
        return playListDTO;
    }



    @Override
    public PlayListDTO getPlayLists(PlayListDTO playListDTO) {

        String userEmail = playListDTO.userEmail;

        User user = ObjectifyInitializer.ofy().load().type(User.class).filter("userEmail",userEmail).first().now();

        if(user.getPlayLists() == null) user.setPlayLists(new ArrayList<>());
        List<String> playLists = new ArrayList<>(user.getPlayLists());

        playListDTO.playLists  = playLists;
        return playListDTO;
    }

    @Override
    public void deletePlayList(PlayListDTO playListDTO) {

        String userEmail = playListDTO.userEmail;
        String playListName = playListDTO.playListName;

        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class).id(playListName+userEmail).now();
        User user = ObjectifyInitializer.ofy().load().type(User.class).filter("userEmail",userEmail).first().now();

        List<String> playLists = user.getPlayLists();
        playLists.remove(playListName);

        ObjectifyInitializer.ofy().delete().entity(playList).now();
        ObjectifyInitializer.ofy().save().entity(user).now();
    }

    @Override
    public PlayListDTO getPlayListItem(PlayListDTO playListDTO) {

        String playListName = playListDTO.playListName;
        String userEmail = playListDTO.userEmail;

        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class).id(playListName+userEmail).now();

        if(playList.getVideoList() == null) playListDTO.ytLinks = new ArrayList<>();
        else playListDTO.ytLinks = new ArrayList<>(playList.getVideoList());

        return playListDTO;
    }

    @Override
    public PlayListDTO addItemToPlayList(PlayListDTO playListDTO) {

        String userEmail = playListDTO.userEmail;
        String playListName = playListDTO.playListName;
        String videoTitle = playListDTO.videoTitle;

        YTLink ytLink = ObjectifyInitializer.ofy().load().type(YTLink.class).id(videoTitle).now();

        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class).id(playListName+userEmail).now();
        List<YTLink> playListItems = playList.getVideoList();
        if(playListItems == null) playListItems = new ArrayList<>();

        playListItems.add(ytLink);
        playList.setVideoList(playListItems);

        ObjectifyInitializer.ofy().save().entity(playList);

        playListDTO.ytLinks = playListItems;
        return playListDTO;
    }

    @Override
    public PlayListDTO removeItemFromPlayList(PlayListDTO playListDTO) {

        String userEmail = playListDTO.userEmail;
        String playListName = playListDTO.playListName;
        String videoTitle = playListDTO.videoTitle;

        YTLink ytLink = ObjectifyInitializer.ofy().load().type(YTLink.class).id(videoTitle).now();

        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class).id(playListName+userEmail).now();
        List<YTLink> playListItems = playList.getVideoList();

        playListItems.remove(ytLink);
        playList.setVideoList(playListItems);

        ObjectifyInitializer.ofy().save().entity(playList);

        playListDTO.ytLinks = playListItems;
        return playListDTO;
    }
}
