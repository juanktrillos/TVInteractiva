package com.jkt.tv.data;

import com.mongodb.BasicDBObject;
import java.util.HashSet;
import java.util.Set;

/**
 * @author juan.trillos
 * @author olarguz
 */
public class FacebookApp extends BasicDBObject {

    public static final String IDUSER = "idUser";
    public static final String IDPASS = "idPass";
    public static final String TOKEN = "token";

    private boolean partial;

    public FacebookApp() {
        partial = false;
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FacebookApp(String idUser, String idPass, String token) {
        this.put(FacebookApp.IDUSER, idUser);
        this.put(FacebookApp.IDPASS, idPass);
        this.put(FacebookApp.TOKEN, token);

        this.markAsPartialObject();
    }

    @Override
    public void markAsPartialObject() {
        Set<String> set = keySet();
        set.remove("_id");

        Set<String> setThis = new HashSet<>();
        setThis.add(IDUSER);

        partial = !set.equals(setThis);
    }

    public String getID() {
        return this.getString(FacebookApp.IDUSER);
    }

    @Override
    public boolean isPartialObject() {
        return partial;
    }
}
