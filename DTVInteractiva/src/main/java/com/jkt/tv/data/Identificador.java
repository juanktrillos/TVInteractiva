/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jkt.tv.data;

import com.mongodb.BasicDBObject;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author JuanCamilo
 */
public class Identificador extends BasicDBObject{

    public static final String ID = "id";
    public static final String TIPO = "tipo";

    private boolean partial;

    public Identificador() {
        partial = false;
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Identificador(String id, String tipo) {
        this.put(Identificador.ID, id);
        this.put(Identificador.TIPO, tipo);

        this.markAsPartialObject();
    }

    @Override
    public void markAsPartialObject() {
        Set<String> set = keySet();
        set.remove("_id");

        Set<String> setThis = new HashSet<>();
        setThis.add(ID);

        partial = !set.equals(setThis);
    }

    public String getID() {
        return this.getString(Identificador.ID);
    }

    public String getTipo() {
        return this.getString(Identificador.TIPO);
    }

    @Override
    public boolean isPartialObject() {
        return partial;
    }    
}
