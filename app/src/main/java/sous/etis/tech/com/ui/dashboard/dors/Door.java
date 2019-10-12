package sous.etis.tech.com.ui.dashboard.dors;

public class Door {
    private String name ;
    private String description ;
    private boolean open ;

    public Door(){

    }
    public Door(String name, String description, boolean open){
        this.name = name ;
        this.description = description ;
        this.open = open ;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
