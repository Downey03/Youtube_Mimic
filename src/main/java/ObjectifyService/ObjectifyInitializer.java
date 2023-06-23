package ObjectifyService;

import Bean.PlayList;
import Bean.PlayListItemMap;
import Bean.User;
import Bean.YTLink;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ObjectifyInitializer implements ServletContextListener {

    public ObjectifyInitializer(){

    }

    public void contextInitialized(ServletContextEvent event) {
        ObjectifyService.init();
        ObjectifyService.register(User.class);
        ObjectifyService.register(PlayList.class);
        ObjectifyService.register(YTLink.class);
        ObjectifyService.register(PlayListItemMap.class);
    }


    public void contextDestroyed(ServletContextEvent event) {
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }
}
