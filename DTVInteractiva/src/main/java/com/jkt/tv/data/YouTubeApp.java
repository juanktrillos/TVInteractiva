package com.jkt.tv.data;

import com.mongodb.BasicDBObject;
import java.util.HashSet;
import java.util.Set;

/**
 * @author juan.trillos
 */
public class YouTubeApp extends BasicDBObject {

    public static final String NOMBRE = "nombre";
    public static final String EDAD = "edad";
    
    private boolean partial;

    public YouTubeApp() {
        this.partial = true;
    }

    @Override
    public void markAsPartialObject() {
        Set<String> set = keySet();
        set.remove("_id");

        Set<String> setThis = new HashSet<>();
        setThis.add(NOMBRE);
        setThis.add(EDAD);

        partial = !set.equals(setThis);

    }
}
