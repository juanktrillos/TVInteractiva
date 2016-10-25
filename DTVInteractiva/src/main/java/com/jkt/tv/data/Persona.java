package com.jkt.tv.data;

import com.mongodb.BasicDBObject;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author olarguz
 */
public class Persona extends BasicDBObject
{

    public static final String NOMBRE = "nombre";
    public static final String EDAD = "edad";
    public static final String PROFESION = "profesion";
    public static final String IDENTIFICACION = "identificacion";
    private boolean partial;

    public Persona()
    {
        this.partial = true;
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Persona(String nombre, int edad, String profesion, String identificacion)
    {
        this.put(Persona.NOMBRE, nombre);
        this.put(Persona.EDAD, edad);
        this.put(Persona.PROFESION, profesion);
        this.put(Persona.IDENTIFICACION, identificacion);

        this.markAsPartialObject();
    }

    public void setEdad(int edad)
    {
        put(EDAD, edad);
    }
    
    public int getEdad()
    {
        return getInt(EDAD);
    }

    @Override
    public void markAsPartialObject()
    {
        Set<String> set = keySet();
        set.remove("_id");

        Set<String> setThis = new HashSet<>();
        setThis.add(NOMBRE);
        setThis.add(EDAD);
        setThis.add(PROFESION);
        setThis.add(IDENTIFICACION);

        partial = !set.equals(setThis);

    }

    @Override
    public boolean isPartialObject()
    {
        return partial;
    }

}
