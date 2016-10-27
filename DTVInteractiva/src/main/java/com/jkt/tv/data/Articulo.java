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
    public static final String ID = "id";

    private boolean partial;
    
    public Articulo ()
    {
        partial = false;
    }
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Articulo (String id)
    {
        this.put(Articulo.ID, id);
        
        this.markAsPartialObject();
    }
    
    @Override
    public void markAsPartialObject() {
        Set<String> set = keySet();
        set.remove("_id");
        
        Set<String> setThis = new HashSet<>();
        setThis.add(ID);
        
        partial =  !set.equals(setThis);
    }
    
    public String getID ()
    {
        return this.getString(Articulo.ID);
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
        boolean cn = getID().equals(other.getID());
        return cn;
    }
    
}
