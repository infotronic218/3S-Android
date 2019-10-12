package sous.etis.tech.com.ui.dashboard;

import java.io.Serializable;

public class ViewType implements Serializable {
    private  String name;
    private String viewClass;
    private int image;


    public ViewType() {
    }

    public ViewType(String name, String description, int image) {
        this.name = name;

        this.viewClass = description;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getViewClass() {
        return viewClass;
    }

    public void setViewClass(String viewClass) {
        this.viewClass = viewClass;
    }
}
