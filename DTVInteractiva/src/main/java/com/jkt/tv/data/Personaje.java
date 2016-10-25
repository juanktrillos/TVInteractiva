
package com.jkt.tv.data;

import com.mongodb.BasicDBObject;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author olarguz
 */
public class Personaje extends BasicDBObject
{
    private static final String NOMBRE = "nombre";
    private static final String AVATAR = "avatar";
    private static final String ANIMACION = "animacion";
    private static final String UBICACION = "ubicacion";
    private static final String DIRECCION = "direccion";
    
    private boolean partial;

    public Personaje()
    {
        partial = false;
    }
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Personaje (String nombre, String avatar, String animacion, Vector ubicacion, Vector direccion)
    {
        this.put(NOMBRE, nombre);
        this.put(AVATAR, avatar);
        this.put(ANIMACION, animacion);
        this.put(UBICACION, ubicacion);
        this.put(DIRECCION, direccion);
        
        this.markAsPartialObject();
    }
    
    public String getNombre (){
        return (String) this.get(NOMBRE);
    }
    
    public String getAvatar (){
        return (String) this.get(AVATAR);
    }
    
    public String getAnimacion (){
        return (String) this.get(ANIMACION);
    }
    
    @Override
    public void markAsPartialObject() {
        Set<String> set = keySet();
        set.remove("_id");
        
        Set<String> setThis = new HashSet<>();
        setThis.add(NOMBRE);
        setThis.add(AVATAR);
        setThis.add(ANIMACION);
        setThis.add(UBICACION);
        setThis.add(DIRECCION);
        
        partial =  !set.equals(setThis);
    }

    @Override
    public boolean isPartialObject() {
        return partial;
    }    
}
