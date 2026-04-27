package Garage.Motorcycle.mail;

import Garage.Motorcycle.db.UsersEntity;

import java.util.HashMap;
import java.util.Map;

//template for all emails
public abstract class AbstractEmailContext {
    private String to;
    private String from;
    private String subject;
    private String email;
    private String templateLocation;
    private final Map<String, Object> context;

    public AbstractEmailContext(){
        this.context=new HashMap<>();
    }
    //overiding this method in AccountVerification file
    public void init(UsersEntity usersEntity, String fromEmail){

    }

    //getter
    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getEmail() {
        return email;
    }

    public String getTemplateLocation() {
        return templateLocation;
    }

    public Map<String, Object> getContext() {
        return context;
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
