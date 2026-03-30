package Garage.Motorcycle.mail;

import Garage.Motorcycle.db.UsersEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//template for all emails
public abstract class AbstractEmailContext {
    private String to;
    private String from;
    private String subject;
    private String email;
    private String templateLocation;
    private Map<String, Object> context;

    public AbstractEmailContext(){
        this.context=new HashMap<>();
    }
    //overiding this method in AccountVerification file
    public void init(UsersEntity usersEntity){

    }

    public Object put(String key, Object value){
        return key==null?null:this.context.put(key, value);
    }
    //setters
    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
    }


}
