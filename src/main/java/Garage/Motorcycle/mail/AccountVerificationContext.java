package Garage.Motorcycle.mail;

import Garage.Motorcycle.db.UsersEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;



public class AccountVerificationContext extends AbstractEmailContext{

    private String token;
    @Override
    public void init(UsersEntity usersEntity, String fromEmail) {
        setTemplateLocation("mailing/email-verification");
        setSubject("Account verification");
        setTo(usersEntity.getEmail());
        setFrom(fromEmail);
    }

    //setting the token

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    //creating a url with verification
    public void generateUrl(String baseUrl, String token){

        //generating an url
        final String url= UriComponentsBuilder.
                fromUriString(baseUrl)//getting base url
                .path("/auth/verify")//endpoint that will work for verify
                .queryParam("token", token).toUriString();//passing token + converting to uri
        //putting to context (variable should be the same as in template)
        put("AuthorizationUrl", url);

    }
}
