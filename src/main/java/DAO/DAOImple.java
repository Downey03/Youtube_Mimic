package DAO;

import Bean.PlayList;
import Bean.PlayListItemMap;
import Bean.User;
import Bean.YTLink;
import DTO.PlayListDTO;
import DTO.UserDTO;
import ObjectifyService.ObjectifyInitializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DAOImple implements DAOInterface{
    @Override
    public PlayListDTO getVideos(String searchKeyword) {

        List<YTLink> ytLinkList = ObjectifyInitializer.ofy().load().type(YTLink.class).list();

        PlayListDTO playListDTO = new PlayListDTO();
        ytLinkList.removeIf(video -> video.getVideoTitle() == null || !video.getVideoTitle().toLowerCase().contains(searchKeyword.trim().toLowerCase()));

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
                .userId(UUID.randomUUID().toString())
                .userName(userDTO.getUserName())
                .userEmail(userEmail)
                .password(userDTO.getPassword())
                .build();

        ObjectifyInitializer.ofy().save().entity(user).now();

        return user.getUserId();
    }

    @Override
    public String validateLogin(UserDTO userDTO) throws Exception {

        User user = ObjectifyInitializer.ofy().load().type(User.class).filter("userEmail",userDTO.getUserEmail()).first().now();

        if(user == null) throw new Exception("Invalid Email");
        if(!user.getPassword().equals(userDTO.getPassword())) throw new Exception("Invalid Password");

        return user.getUserId();
    }

    @Override
    public PlayListDTO createPlayList(PlayListDTO playListDTO) throws Exception {

        String userId = playListDTO.userId;
        String playListName = playListDTO.playListName;

        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class)
                .filter("playListName",playListName).filter("userId",userId)
                        .first().now();

        if(playList != null) throw  new Exception("PlayList Already Found");

        playList = PlayList.builder()
                .playListId(UUID.randomUUID().toString())
                .playListName(playListName)
                .userId(userId)
                .build();

        ObjectifyInitializer.ofy().save().entity(playList);

        return getPlayLists(playListDTO);

    }



    @Override
    public PlayListDTO getPlayLists(PlayListDTO playListDTO) {

        String userId = playListDTO.userId;

        List<PlayList> playLists = ObjectifyInitializer.ofy().load().type(PlayList.class).filter("userId",userId).list();

        playListDTO.playLists = new ArrayList<>();
        for(PlayList name : playLists)
            playListDTO.playLists.add(name.getPlayListName());

        return playListDTO;
    }

    @Override
    public PlayListDTO deletePlayList(PlayListDTO playListDTO) {

        String userId = playListDTO.userId;
        String playListName = playListDTO.playListName;
        System.out.println(playListName);

        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class)
                .filter("userId",userId).filter("playListName",playListName)
                .first().now();



        ObjectifyInitializer.ofy().delete().type(PlayList.class).id(playList.getPlayListId()).now();

        List<PlayListItemMap> playListItemMaps = ObjectifyInitializer.ofy().load().type(PlayListItemMap.class).list();

//        playListItemMaps.parallelStream().map(item -> ObjectifyInitializer.ofy().delete().entity(item));

        for(PlayListItemMap item : playListItemMaps){
            ObjectifyInitializer.ofy().delete().entity(item);
        }

        return getPlayLists(playListDTO);
    }

    public PlayListDTO getPlayListItem(PlayListDTO playListDTO,PlayList playList){

        List<PlayListItemMap> playListItemMaps = ObjectifyInitializer.ofy().load().type(PlayListItemMap.class)
                .filter("playListId",playList.getPlayListId()).list();

        playListDTO.ytLinks = new ArrayList<>();

        for(PlayListItemMap item : playListItemMaps){
            YTLink ytLink = new YTLink();
            ytLink.setVideoLink(item.getVideoLink());
            ytLink.setVideoTitle(item.getVideoTitle());
            ytLink.setVideoThumbnail(item.getVideoThumbnail());
            playListDTO.ytLinks.add(ytLink);
        }

        return playListDTO;
    }

    @Override
    public PlayListDTO getPlayListItem(PlayListDTO playListDTO) {

        String playListName = playListDTO.playListName;
        String userId = playListDTO.userId;

        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class)
                .filter("playListName",playListName).filter("userId",userId)
                .first().now();

        List<PlayListItemMap> playListItemMaps = ObjectifyInitializer.ofy().load().type(PlayListItemMap.class)
                .filter("playListId",playList.getPlayListId()).list();

        System.out.println(Arrays.toString(playListItemMaps.toArray()));

        playListDTO.ytLinks = new ArrayList<>();

        for(PlayListItemMap item : playListItemMaps){
            YTLink ytLink = new YTLink();
            ytLink.setVideoLink(item.getVideoLink());
            ytLink.setVideoTitle(item.getVideoTitle());
            ytLink.setVideoThumbnail(item.getVideoThumbnail());
            playListDTO.ytLinks.add(ytLink);
        }

        return playListDTO;
    }

    @Override
    public PlayListDTO addItemToPlayList(PlayListDTO playListDTO) {

        String userId = playListDTO.userId;
        String playListName = playListDTO.playListName;
        String videoTitle = playListDTO.videoTitle;

        YTLink ytLink = ObjectifyInitializer.ofy().load().type(YTLink.class)
                .filter("videoTitle",videoTitle)
                .first().now();

        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class)
                .filter("playListName",playListName).filter("userId",userId)
                .first().now();

        PlayListItemMap playListItemMap = PlayListItemMap.builder()
                .playListItemMapId(UUID.randomUUID().toString())
                .playListId(playList.getPlayListId())
                .videoLink(ytLink.getVideoLink())
                .videoTitle(ytLink.getVideoTitle())
                .videoThumbnail(ytLink.getVideoThumbnail())
                .build();

        ObjectifyInitializer.ofy().save().entity(playListItemMap).now();

        return getPlayListItem(playListDTO,playList);

    }

    @Override
    public PlayListDTO removeItemFromPlayList(PlayListDTO playListDTO) {

        String userId = playListDTO.userId;
        String playListName = playListDTO.playListName;
        String videoTitle = playListDTO.videoTitle;

        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class)
                .filter("playListName",playListName).filter("userId",userId)
                .first().now();

        System.out.println(playList.toString());

        PlayListItemMap playListItemMap = ObjectifyInitializer.ofy().load().type(PlayListItemMap.class)
                        .filter("playListId",playList.getPlayListId()).filter("videoTitle",videoTitle)
                        .first().now();

        System.out.println(playListItemMap.toString());

        ObjectifyInitializer.ofy().delete().entity(playListItemMap);

        return getPlayListItem(playListDTO);
    }
}
