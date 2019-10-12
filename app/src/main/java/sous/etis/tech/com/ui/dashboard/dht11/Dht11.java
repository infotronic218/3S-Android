package sous.etis.tech.com.ui.dashboard.dht11;

public class Dht11 {
    private String name ;
    private String temperature ;
    private  String humidity;

    public Dht11() {
    }

    public Dht11(String name, String temperature, String humidity) {
        this.name = name;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
