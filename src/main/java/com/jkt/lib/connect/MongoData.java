
package com.jkt.lib.connect;

/**
 *
 * @author olarguz
 */
public class MongoData
{
    
    private String userName;
    private String password;
    private int port;
    private String dbName;
    private String host;

    public MongoData(String userName, String password, String host, int port, String dbName)
    {
        this.userName = userName;
        this.password = password;
        this.host = host;
        this.port = port;
        this.dbName = dbName;
    }

    /**
     * Get the value of host
     *
     * @return the value of host
     */
    public String getHost()
    {
        return host;
    }

    /**
     * Set the value of host
     *
     * @param host new value of host
     */
    public void setHost(String host)
    {
        this.host = host;
    }

    /**
     * Get the value of dbName
     *
     * @return the value of dbName
     */
    public String getDbName()
    {
        return dbName;
    }

    /**
     * Set the value of dbName
     *
     * @param dbName new value of dbName
     */
    public void setDbName(String dbName)
    {
        this.dbName = dbName;
    }

    /**
     * Get the value of port
     *
     * @return the value of port
     */
    public int getPort()
    {
        return port;
    }

    /**
     * Set the value of port
     *
     * @param port new value of port
     */
    public void setPort(int port)
    {
        this.port = port;
    }

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Get the value of userName
     *
     * @return the value of userName
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * Set the value of userName
     *
     * @param userName new value of userName
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getMongoURI()
    {
        String mongoURI = "mongodb://";
        mongoURI += userName + ":" + password;
        mongoURI += "@ds0" + port;
        mongoURI += "." + host + ":"+ port;
        mongoURI += "/" + dbName;
        return mongoURI;
    }
}
