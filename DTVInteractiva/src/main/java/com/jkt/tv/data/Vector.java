
package com.jkt.tv.data;

import com.mongodb.BasicDBObject;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author olarguz
 */
public class Vector extends BasicDBObject
{
    public static final String X = "x";
    public static final String Y = "y";
    public static final String Z = "z";
    
    private boolean partial;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Vector (double x, double y, double z)
    {
        this.put(X, x);
        this.put(Y, y);
        this.put(Z, z);
        
        this.markAsPartialObject();
    }
    
    @Override
    public void markAsPartialObject() {
        Set<String> set = keySet();
        set.remove("_id");
        
        Set<String> setThis = new HashSet<>();
        setThis.add(X);
        setThis.add(Y);
        setThis.add(Z);
        
        partial =  !set.equals(setThis);
    }

    @Override
    public boolean isPartialObject() {
        return partial;
    }    
}
