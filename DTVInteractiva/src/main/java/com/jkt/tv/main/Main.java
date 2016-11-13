package com.jkt.tv.main;

//import com.oag.servicio.mongolib.driven.MongoHandler;
import com.jkt.lib.driven.MongoHandler;
import com.jkt.tv.data.Identificador;
import com.mongodb.BasicDBObject;
import com.panamahitek.PanamaHitek_Arduino;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author olarguz
 * @author juan.trillos
 */
public class Main extends Application implements SerialPortEventListener {

    private final WebView wv = new WebView();
    private final WebEngine we = wv.getEngine();

    private final String index = "index.html";
    private final String list = "list.html";
    private Stage stage;

    private PanamaHitek_Arduino arduino;
    private String serialPort = "";
    private String message = "";

    /**
     * @param args
     */
    public static void main(String args[]) {

        launch(args);

        //<editor-fold defaultstate="collapsed" desc="Arduino">
        /* Arduino arduino;
         String SerialPort = "COM11";

         //conexion con la base de datos
         //            mongoHandler = new MongoHandler("TVInteractiva");
         //Input/Output Arduino
         arduino = new Arduino(SerialPort);

         //Manipulacion de la Base de Datos en MongoDB
         //            insertInDB(mongoHandler);
         //            updateInDB(mongoHandler);
         //            showFromDB(mongoHandler);*/
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Facebook">
        /*ConfigurationBuilder cb = new ConfigurationBuilder();
         cb.setDebugEnabled(true);
         cb.setOAuthAppId("1297407430303696");
         cb.setOAuthAppSecret("4dde7175e1787be7c61337ffeaf3ad21");
         cb.setOAuthAccessToken("EAACEdEose0cBAJx4DiDH5ZAw11sESVo"
         + "ICCU8JklZAOrYujtmZAGVoOZCgJtH9PWPMKWVdAz7r26f"
         + "NFBUcDt8yO9F35RM9uZAdDmHIJnHu6VtuIBulLSKexXvp"
         + "b3ZChxamfHi8AErpqlykzrdeX5bxlhinnSKZBdpJZCVxu"
         + "wuYsaZBDAZDZD");
         cb.setOAuthPermissions("email,publish_stream,...");
         FacebookFactory ff = new FacebookFactory(cb.build());
         Facebook face = ff.getInstance();
         AccessToken a = face.getOAuthAccessToken();
         System.out.println(a.toString());
        
         try {
         face.postStatusMessage("Publish Test");
         } catch (FacebookException ex) {
         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
         }*/
//</editor-fold>
    }

    public void connection() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        arduino = new PanamaHitek_Arduino();
        stage = primaryStage;
        List<String> ports = arduino.getSerialPorts();
        for (String port : ports) {
            serialPort = port;
        }
        arduino.arduinoRX(serialPort, 9600, this);
        //arduino.killArduinoConnection();

        Scene scene = new Scene(wv);
        String content = read(index);
        we.loadContent(content);

        wv.setPrefSize(1080, 680);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        try {
            arduino.killArduinoConnection();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String read(String page) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/main/resources/" + page));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        String content = contentBuilder.toString();
        return content;
    }

    @Override
    public void serialEvent(SerialPortEvent ev) {
        if (arduino.isMessageAvailable()) {
            String cad = arduino.printMessage();
            message = cad;

//            insertDB();
            interactuar();
        }
    }

    public void insertDB() {
        try {
            MongoHandler mongo = new MongoHandler("TVInteractiva");
//            mongo.insert(new Identificador(message, "Perfil"));
//            mongo.insert(new Identificador(message, "Publicar"));

        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void interactuar() {
//        try {
//            MongoHandler mongo = new MongoHandler("TVInteractiva");
//            LinkedList<Identificador> find = (LinkedList<Identificador>) mongo.find(Identificador.class, "id", message);
//            
//            for (Identificador rfid : find) {
//                if(rfid.getTipo().equals("Perfil")){
//                    update();
//                }
//            }
//            
//        } catch (UnknownHostException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
        update();
    }

    public void update() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                we.loadContent(read(list));
            }
        });
    }
}
