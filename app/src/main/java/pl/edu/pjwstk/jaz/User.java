package pl.edu.pjwstk.jaz;

import java.util.HashSet;
import java.util.Set;

public class User {
    public String username;
    public String password;
    public String name;
    public Set<String> Authorities;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        Authorities = new HashSet<>();
        Authorities.add("basic");
    }

    public User(String username, String password,Set<String> roles) {
        this.username = username;
        this.password = password;
        Authorities = roles;
    }

    @Override
    public String toString() {
        return username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getAuthorities() {
        return Authorities;
    }

    public User(Set<String> Authorities) {
        this.Authorities = Authorities;
    }

    public void addAuthorities(String permission){
        Authorities.add(permission);
    }
}
