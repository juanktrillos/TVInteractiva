package com.jkt.tv.main;

//import com.oag.servicio.mongolib.driven.MongoHandler;
import com.jkt.lib.driven.MongoHandler;
import com.jkt.tv.data.Identificador;
import com.panamahitek.PanamaHitek_Arduino;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
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

    private PanamaHitek_Arduino arduino;
    private String serialPort = "";
    String ident[] = new String[2];

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
        List<String> ports = arduino.getSerialPorts();
        for (String port : ports) {
            serialPort = port;
        }
        arduino.arduinoRX(serialPort, 9600, this);
        //arduino.killArduinoConnection();

        Scene scene = new Scene(wv);
//        String content = readHTML(index);
        we.load(getClass().getResource("/index.html").toExternalForm());
//        we.loadContent(content);

        wv.setPrefSize(1080, 680);
        primaryStage.setScene(scene);
        primaryStage.show();
//        initFX();
    }

    @Override
    public void stop() {
        try {
            arduino.killArduinoConnection();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="readBufferedHTML">
    /*public String readHTML(String page) {
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
    System.out.println(content);
    return content;
    }*/
//</editor-fold>
    
    @Override
    public void serialEvent(SerialPortEvent ev) {
        if (arduino.isMessageAvailable()) {
            String cad = arduino.printMessage();
//            insertDB(cad);
            interactuar(cad);
        }
    }

    public void insertDB(String id) {
        try {
            MongoHandler mongo = new MongoHandler("TVInteractiva");
//            mongo.insert(new Identificador(id, "Perfil"));
//            mongo.insert(new Identificador(id, "Publicar"));

        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void interactuar(String id) {

        if (id.equals(ident[0])) {
            loadList();
        } else if (id.equals(ident[1])) {
            String loc = we.getLocation();
            String[] split = loc.split(":");
            if (split[0].equals("https")) {
                publish();
            }
        } else {
            try {
                MongoHandler mongo = new MongoHandler("TVInteractiva");
                LinkedList<Identificador> find = (LinkedList<Identificador>) mongo.find(Identificador.class, ""
                        + "id", id);

                for (Identificador rfid : find) {
                    if (rfid.getTipo().equals("Perfil")) {
                        ident[0] = rfid.getID();
                        loadList();
                    } else if (rfid.getTipo().equals("Publicar")) {
                        ident[1] = rfid.getID();
                        String loc = we.getLocation();
                        String[] split = loc.split(":");
                        if (split[0].equals("https")) {
                            publish();
                        }
                    }
                }
            } catch (UnknownHostException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
//        loadList();
        }
    }

    public void loadList() {

        Platform.runLater(() -> {
            //                we.loadContent(readHTML(list));
            we.load(getClass().getResource("/list.html").toExternalForm());
        });
    }

    /*private void initFX() {
    //        createScene();
    we.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
    @Override
    public void changed(ObservableValue ov, State oldState, State newState) {
    if (newState == Worker.State.SUCCEEDED) {
    EventListener listener = new EventListener() {
    @Override
    public void handleEvent(Event ev) {
    String domEventType = ev.getType();
    //                            System.err.println("EventType: " + domEventType);
    if (domEventType.equals("click")) {
    String href = ((Element) ev.getTarget()).getAttribute("href");
    //////////////////////
    // here do what you want with that clicked event
    // and the content of href
    //////////////////////
    //                                we.loadContent(readHTML(href));
    }
    }
    };
    
    Document doc = we.getDocument();
    NodeList nodeList = doc.getElementsByTagName("a");
    for (int i = 0; i < nodeList.getLength(); i++) {
    ((EventTarget) nodeList.item(i)).addEventListener("click", listener, false);
    //((EventTarget) nodeList.item(i)).addEventListener(EVENT_TYPE_MOUSEOVER, listener, false);
    //((EventTarget) nodeList.item(i)).addEventListener(EVENT_TYPE_MOUSEOVER, listener, false);
    }
    }
    }
    });
    }*/
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void publish() {

        String link = we.getLocation();
        new Publish(link);
    }
}
