package com.jkt.lib.driven;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.jkt.lib.connect.ConnectFactory;
import com.jkt.lib.connect.MongoData;
import java.net.UnknownHostException;
import java.util.LinkedList;
import org.bson.types.ObjectId;

/**
 *
 * @author oarcila
 */
public class MongoHandler
{

    private final DB db;

    public MongoHandler(String key) throws UnknownHostException
    {
        MongoData data = ConnectFactory.loadConnectionData(key);
        db = ConnectFactory.createConnection(data, true);
    }

    public void insert(BasicDBObject object)
    {
        DBCollection collection = db.getCollection(object.getClass().getSimpleName());

        collection.insert(object);
    }

    public void update(Class<? extends BasicDBObject> aClass, CriterioActualizacion criterioActualizacion)
    {
        String cadCriterio = criterioActualizacion.getNombreCriterio();
        String valCriterio = criterioActualizacion.getValorCriterio();
        LinkedList<? extends BasicDBObject> r = (LinkedList<? extends BasicDBObject>) find(aClass, cadCriterio, valCriterio);

        if (r.size() == 1)
        {
            BasicDBObject e = r.getFirst();
            BasicDBObject cp = (BasicDBObject) e.clone();
            String cadAtributo = criterioActualizacion.getNombreAtributo();
            Object valAtributo = criterioActualizacion.getValorAtributo();
            cp.put(cadAtributo, valAtributo);
            cp.markAsPartialObject();

            DBCollection collection = db.getCollection(aClass.getSimpleName());
            collection.update(e, cp);
        }
    }

    public LinkedList<? extends BasicDBObject> findAll(Class<? extends BasicDBObject> clase)
    {
        LinkedList<BasicDBObject> r = new LinkedList<>();
        DBCollection collection = db.getCollection(clase.getSimpleName());
        collection.setObjectClass(clase);
        DBCursor cursor = collection.find();
        while (cursor.hasNext())
        {
            DBObject objectAux = cursor.next();
            r.add((BasicDBObject) objectAux);
        }
        return r;
    }

    /**
     * Permite buscar elementos de la <code>Clase</code> de terminada por
     * <b>clase</b>, usando un atributo en particular. Los elementos se buscan
     * usando como criterio de búsqueda el valor de un atributo determinado.
     *
     * @param clase
     * @param atribute
     * @param data
     * @return
     */
    public LinkedList<? extends BasicDBObject> find(Class<? extends BasicDBObject> clase, String atribute, Object data)
    {
        LinkedList<BasicDBObject> r = new LinkedList<>();
        BasicDBObject query = new BasicDBObject(atribute, data);
        if ( atribute.equals("_id"))
        {
            query = new BasicDBObject();
            String value = (String)data;
            query.put("_id", new ObjectId(value));
        }
        DBCollection collection = db.getCollection(clase.getSimpleName());
        collection.setObjectClass(clase);
        DBCursor cursor = collection.find(query);
        while (cursor.hasNext())
        {
            DBObject objectAux = cursor.next();
            r.add((BasicDBObject) objectAux);
        }
        return r;
    }

    public LinkedList<? extends BasicDBObject> find(Class<? extends BasicDBObject> aClass, String atribute, Number min, Number max)
    {
        LinkedList<BasicDBObject> r = new LinkedList<>();
        BasicDBObject query = new BasicDBObject(atribute, new BasicDBObject("$gt", min).append("$lte", max));
        DBCollection collection = db.getCollection(aClass.getSimpleName());
        collection.setObjectClass(aClass);
        DBCursor cursor = collection.find(query);
        while (cursor.hasNext())
        {
            DBObject objectAux = cursor.next();
            r.add((BasicDBObject) objectAux);
        }
        return r;
    }
}
