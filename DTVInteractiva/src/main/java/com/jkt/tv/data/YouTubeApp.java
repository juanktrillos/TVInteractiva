package com.jkt.tv.data;

import com.mongodb.BasicDBObject;
import java.util.HashSet;
import java.util.Set;

/**
 * @author juan.trillos
 * @author olarguz
 */
public class YouTubeApp extends BasicDBObject {

    public static final String NOMBRE = "nombre";
    public static final String EDAD = "edad";

    private boolean partial;

    public YouTubeApp() {
        this.partial = true;
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public YouTubeApp(String nombre, int edad) {
        this.put(YouTubeApp.NOMBRE, nombre);
        this.put(YouTubeApp.EDAD, edad);

        this.markAsPartialObject();
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

    @Override
    public boolean isPartialObject() {
        return partial;
    }
}
