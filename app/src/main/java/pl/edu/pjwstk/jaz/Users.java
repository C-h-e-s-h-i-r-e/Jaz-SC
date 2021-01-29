package pl.edu.pjwstk.jaz;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class Users {
    HashMap<String,User> userHashMap = new HashMap<String,User>();

    public void add(User user)
    {
        userHashMap.put(user.getUsername(),user);
    }

    public String getUsername(String username)
    {
        return userHashMap.get(username).getUsername();
    }

    public boolean nameExist(String username)
    {
        return userHashMap.containsKey(username);
    }

    public User getUser(String username){return userHashMap.get(username);}

    // remove
    public ArrayList<User> usersInHashMap() {

        return new ArrayList<User>( userHashMap.values());
    }

    public boolean passwordSame(String username, String password)
    {
        return  password.equals(userHashMap.get(username).getPassword());
    }
    public boolean isEmpty(){
        return userHashMap.size() <= 0;
    }


}
