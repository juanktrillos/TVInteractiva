
package com.jkt.lib.driven;

import com.mongodb.BasicDBObject;

/**
 *
 * @author oarcila
 */
public class CriterioActualizacion extends BasicDBObject
{
    public static final String NOMBRECRITERIO = "nombreCriterio";
    public static final String VALORCRITERIO = "valorCriterio";
    public static final String NOMBREATRIBUTO = "nombreAtributo";
    public static final String VALORATRIBUTO = "valorAtributo";
    
    public CriterioActualizacion ()
    {
    }
    
    public void setCriterio(String atributo, String valor)
    {
        put(NOMBRECRITERIO, atributo);
        put(VALORCRITERIO, valor);
    }
    
    public void setNuevoValor (String atributo, Object valor)
    {
        put(NOMBREATRIBUTO, atributo);
        put(VALORATRIBUTO, valor);
    }
    
    public String getNombreCriterio ()
    {
        return getString(NOMBRECRITERIO);
    }
    
    public String getValorCriterio ()
    {
        return getString(VALORCRITERIO);
    }
    
    public String getNombreAtributo ()
    {
        return getString(NOMBREATRIBUTO);
    }
    
    public Object getValorAtributo ()
    {
        return get(VALORATRIBUTO);
    }
}
