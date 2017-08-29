package model;

import java.util.Date;

/**
 * Created by shawn on 2017/8/17.
 */

public class Friend {
    private String id;
    private String name;
    private String email;
    private String birthday;

    public Friend(){}

    public Friend( String id, String name, String email, String birthday){
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
      //  this.birthday = birthday;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthday(){ return birthday;}

    public void setBirthday(String birthday){}
}
