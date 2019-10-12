package sous.etis.tech.com.models;

import java.io.Serializable;

public class InstallationUser implements Serializable {
    private String email ;
    private String username;
    private String name;
    private String password ;
    private String token ;

    public InstallationUser(String name, String username,String email, String password,  String token){
        this.email = email ;
        this.password = password ;
        this.name = name ;
        this.username = username;
        this.token = token ;
    }
    public  InstallationUser(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
