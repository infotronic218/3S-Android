package sous.etis.tech.com.ui.notifications;

import java.util.Collections;
import java.util.Comparator;

public class Notification {
    private String title ;
    private String body ;
    private String date ;

    public Notification() {
    }

    public Notification(String title, String body, String date) {
        this.title = title;
        this.body = body;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public static class  ShortNotification implements Comparator<Notification> {

        @Override
        public int compare(Notification notification, Notification t1) {
            return Long.valueOf(t1.getDate()).compareTo(Long.valueOf(notification.getDate()));
        }


    }
}
