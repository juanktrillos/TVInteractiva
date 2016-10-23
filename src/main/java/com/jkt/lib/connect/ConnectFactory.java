
package com.jkt.lib.connect;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;
import com.jkt.lib.util.file.FileDriver;
import java.net.UnknownHostException;
import java.util.Iterator;
import org.bson.types.BasicBSONList;

/**
 *
 * @author olarguz
 */
public class ConnectFactory
{

    public static DB createConnection(MongoData data, boolean isCloudConnection) throws UnknownHostException
    {
        MongoClient client = null;
        if (isCloudConnection)
        {
            String mongoCloudURI = data.getMongoURI();
            MongoClientURI uri = new MongoClientURI(mongoCloudURI);
            client = new MongoClient(uri);
        } 
        else
        {
            client = new MongoClient(data.getHost());
        }
        return client.getDB(data.getDbName());
    }
    
    public static MongoData loadConnectionData ( String key)
    {
        DBObject fileData = FileDriver.leerArchivo ("db-data.json");
        BasicBSONList collection = (BasicBSONList)(DBObject)fileData.get("dbs");
        DBObject aux = (DBObject)JSON.parse("{\"db-name\":\"" + key +"\"}");
        DBObject objectData = searchObjectInCollection ( collection, aux);
        return createMongoData ( objectData);
    }

    private static DBObject searchObjectInCollection(BasicBSONList l, DBObject objectKey)
    {
        DBObject r = null;
        String objectValue = (String) objectKey.get("db-name");
        for (Iterator<Object> it = l.iterator(); r == null && it.hasNext();)
        {
            DBObject objectAux = (DBObject)it.next();
            String objectAuxValue = (String) objectAux.get("db-name");
            if ( objectValue.equals(objectAuxValue))
            {
                r = objectAux;
            }
        }
        return r != null ? (DBObject) r.get("data"): null;
    }

    private static MongoData createMongoData(DBObject objectData)
    {
        String userName = (String) objectData.get("userName");
        String passWord = (String) objectData.get("passWord");
        String server = (String) objectData.get("server");
        String db = (String) objectData.get("db");
        int portNum = (int) objectData.get("port");
        return new MongoData(userName, passWord, server, portNum, db);
    }
}
