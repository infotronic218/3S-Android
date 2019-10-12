package sous.etis.tech.com.ui.dashboard.lamps;

import java.io.Serializable;

public class Lamp implements  Serializable {
    private  String name ;
    private  boolean state ;

    public Lamp(String name, boolean state) {
        this.name = name;
        this.state = state;
    }

    public Lamp() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
