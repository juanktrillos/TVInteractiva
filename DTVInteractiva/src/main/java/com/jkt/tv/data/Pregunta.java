
package com.jkt.tv.data;

import com.mongodb.BasicDBObject;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author oarcila
 */
public class Pregunta extends BasicDBObject
{
    private static final String CATEGORIA = "categoria";
    private static final String TEXTO = "texto";
    
    private boolean partial;

    public Pregunta()
    {
        partial = false;
    }
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Pregunta (String categoria, String texto)
    {
        this.put(CATEGORIA, categoria);
        this.put(TEXTO, texto);
        
        this.markAsPartialObject();
    }
    
    @Override
    public void markAsPartialObject() {
        Set<String> set = keySet();
        set.remove("_id");
        
        Set<String> setThis = new HashSet<>();
        setThis.add(CATEGORIA);
        setThis.add(TEXTO);
        
        partial =  !set.equals(setThis);
    }

    @Override
    public boolean isPartialObject() {
        return partial;
    }    
}
