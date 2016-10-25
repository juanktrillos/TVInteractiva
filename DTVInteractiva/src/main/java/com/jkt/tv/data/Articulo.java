package com.jkt.tv.data;

import com.mongodb.BasicDBObject;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author olarguz
 */
public class Articulo extends BasicDBObject
{
    public static final String NOMBRE = "nombre";
    public static final String REFERENCIA = "referencia";
    public static final String MODELO = "modelo";

    private boolean partial;
    
    public Articulo ()
    {
        partial = false;
    }
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Articulo (String nombre, String referencia, int Modelo)
    {
        this.put(Articulo.NOMBRE, nombre);
        this.put(Articulo.REFERENCIA, referencia);
        this.put(Articulo.MODELO, Modelo);
        
        this.markAsPartialObject();
    }
    
    @Override
    public void markAsPartialObject() {
        Set<String> set = keySet();
        set.remove("_id");
        
        Set<String> setThis = new HashSet<>();
        setThis.add(NOMBRE);
        setThis.add(REFERENCIA);
        setThis.add(MODELO);
        
        partial =  !set.equals(setThis);
    }
    
    public String getNombre ()
    {
        return this.getString(Articulo.NOMBRE);
    }

    public String getReferencia ()
    {
        return this.getString(Articulo.REFERENCIA);
    }

    public String getModelo ()
    {
        return this.getString(Articulo.MODELO);
    }

    @Override
    public boolean isPartialObject() {
        return partial;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 53 * hash + (this.partial ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Articulo other = (Articulo) obj;
        boolean cn = getNombre().equals(other.getNombre());
        boolean cr = getReferencia().equals(other.getReferencia());
        boolean cm = getModelo().equals(other.getModelo());
        return cn && cr && cm;
    }
    
}
